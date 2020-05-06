package com.group14.termproject.server.game.model.spaceship;

import com.group14.termproject.server.game.util.Vector2D;

public interface PlayerSpaceship extends Spaceship {
    double getScore();

    void setScore(double cumulativeScore);

    /**
     * Works exactly like {@link Spaceship#onEnemyKilled(Spaceship)}.
     * <p>
     * In addition, player's score is increased based on enemy's quality which is found by
     * {@link Spaceship#getScoreValue()}.
     *
     * @param spaceship enemy spaceship that is killed.
     */
    @Override
    void onEnemyKilled(Spaceship spaceship);

    /**
     * <p>
     * Player's spaceship should always follow the mouse. The method should be called each time mouse position is
     * changed.
     * </p>
     *
     * <p>
     * However, the position of the spaceship is not instantly changed. The spaceship begins to move towards {@code
     * newPosition}. The time taken until it reaches the new position depends on spaceship's movement speed.
     * </p>
     *
     * @param newPosition new position which is relative to grid
     */
    void onMousePositionChange(Vector2D newPosition);
}
