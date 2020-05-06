package com.group14.termproject.server.game.model;

import com.group14.termproject.server.game.util.Rectangle;
import com.group14.termproject.server.game.util.Vector2D;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class GameObjectImpl implements GameObject {
    private final String id;
    /**
     * Stores position and the area of the object in 2D space.
     */
    protected final Rectangle rigidBody;
    protected final Vector2D rotation;
    protected Vector2D velocity = Vector2D.zero();
    protected boolean destroyed = false;

    protected GameObjectImpl(Rectangle rigidBody, Vector2D rotation) {
        this.rigidBody = rigidBody;
        this.rotation = rotation;
        id = UUID.randomUUID().toString();
    }

    @Override
    public Vector2D getPosition() {
        return this.rigidBody.center;
    }

    @Override
    public void updateOnFrame() {
        this.move();
    }

    /**
     * Changes position of the object if it has velocity.
     */
    protected void move() {
        if (velocity.equals(Vector2D.zero())) return;
        Vector2D position = this.rigidBody.center;
        Vector2D newPosition = position.getAdded(velocity);
        this.rigidBody.reposition(newPosition);
    }

    @Override
    public boolean isColliding(GameObject gameObject) {
        return this.rigidBody.isOverlapping(((GameObjectImpl) gameObject).rigidBody);
    }

}
