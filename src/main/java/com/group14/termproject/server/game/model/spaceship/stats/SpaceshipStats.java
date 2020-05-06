package com.group14.termproject.server.game.model.spaceship.stats;

import com.group14.termproject.server.game.util.enums.SpaceshipTier;

public interface SpaceshipStats {
    SpaceshipTier getTier();

    double getMaxHealth();

    double getHealth();

    double getMovementSpeed();

    /**
     * Should be called each frame so that scheduled activities such as health regeneration can be done.
     */
    void updateOnFrame();

    /**
     * <p>
     * Calculates elapsed time between <b>last attack time</b> and now. If elapsed time is enough, which is determined by
     * attack speed, it returns true and resets <b>last attack time</b> immediately.
     * </p>
     *
     * <p>
     * Note: It would be better to call the method each frame (after calling {@link SpaceshipStats#updateOnFrame()}
     * for more precise timing.
     * </p>
     *
     * @return true if attacking is permitted, false otherwise.
     */
    boolean tryAttacking();

    /**
     * @return amount of damage that can be caused by a basic attack of the owner.
     */
    double getAttackPower();

    /**
     * When fired, bullets quality is randomly decided. Randomness depends on spaceship's tier.
     * Spaceships with higher tier generates enhanced bullets more possibly.
     *
     * @return true if next generated bullet can be enhanced, false otherwise.
     */
    boolean canGenerateEnhancedBullet();

    /**
     * Should be called whenever the owner spaceship is attacked.
     *
     * @param damage Damage tried to be dealt by the attacker.
     */
    void onDamageTaken(double damage);

    /**
     * @return whether health is 0.
     */
    boolean isDead();
}
