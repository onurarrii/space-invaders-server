package com.group14.termproject.server.game.model;

import com.group14.termproject.server.game.util.Rectangle;
import com.group14.termproject.server.game.util.Vector2D;

public interface GameObject {
    String getId();

    /**
     * Should be called each frame to update object.
     * Handles basic physical activities such as movement, rotation and collision etc.
     * It is implemented by every object in the game.
     */
    void updateOnFrame();

    /**
     * Should be called each second.
     * It only affects objects having scheduled procedures such as health generation and skill cooldown etc.
     */
    default void updateOnSecond() {
    }

    Vector2D getPosition();

    Vector2D getRotation();

    Rectangle getRigidBody();

    Vector2D getVelocity();

    boolean isDestroyed();

    boolean isColliding(GameObject gameObject);
}
