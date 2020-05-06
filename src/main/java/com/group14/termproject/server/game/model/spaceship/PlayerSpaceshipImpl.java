package com.group14.termproject.server.game.model.spaceship;

import com.group14.termproject.server.game.model.spaceship.stats.SpaceshipStats;
import com.group14.termproject.server.game.util.Rectangle;
import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.game.util.enums.SpaceshipType;
import lombok.Getter;
import lombok.Setter;

@Getter
public final class PlayerSpaceshipImpl extends SpaceshipImpl implements PlayerSpaceship {
    private final double userId;
    @Setter
    private double score = 0;
    private Vector2D destination;

    public PlayerSpaceshipImpl(Rectangle rigidBody, Vector2D rotation, SpaceshipStats spaceshipStats,
                               SpaceshipType spaceshipType, double userId) {
        super(rigidBody, rotation, spaceshipStats, spaceshipType);
        this.userId = userId;
    }

    @Override
    public void updateOnFrame() {
        this.updateVelocity();
        super.updateOnFrame();
    }

    private void updateVelocity() {
        if (destination == null) return;
        Vector2D positionToDestination = destination.getSubtracted(getPosition());
        Vector2D velocityDirection = positionToDestination.normalized();
        double velocityMagnitude = Math.min(spaceshipStats.getMovementSpeed(), positionToDestination.magnitude());
        this.velocity = velocityDirection.getMultiplied(velocityMagnitude);
    }

    @Override
    public void onEnemyKilled(Spaceship spaceship) {
        this.score += spaceship.getScoreValue();
    }

    @Override
    public void onMousePositionChange(Vector2D newPosition) {
        this.destination = newPosition;
    }
}
