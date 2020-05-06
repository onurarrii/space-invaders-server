package com.group14.termproject.server.game.model.bullet.stats;

import com.group14.termproject.server.game.util.enums.BulletType;

public interface BulletStats {

    /**
     * @return magnitude of bullet's velocity.
     */
    double getSpeed();

    BulletType getType();

    /**
     * Possibly increases or decreases bullet's damage depending on its quality and how many spaceships it hit before.
     *
     * @param baseDamage base damage that the bullet have.
     * @return modified damage value.
     * @throws IllegalStateException thrown when bullet's hit count limit is exceeded.
     */
    double getActualDamage(double baseDamage) throws IllegalStateException;

    /**
     * Should be called after each hit. If bullet's hit count limit is reached, then it should be destroyed as it
     * cannot deal more damage and its lifetime is considered as over.
     *
     * @return true if bullet's lifetime is over and should be destroyed, false otherwise.
     */
    boolean shouldDestroyAfterHit();
}
