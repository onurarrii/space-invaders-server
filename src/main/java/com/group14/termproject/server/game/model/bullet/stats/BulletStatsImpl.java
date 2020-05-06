package com.group14.termproject.server.game.model.bullet.stats;


import com.group14.termproject.server.game.util.enums.BulletType;
import lombok.Getter;

public class BulletStatsImpl implements BulletStats {
    @Getter
    private final double damageModifyingRatio;
    /**
     * Indicates how many enemies it can hit before it explodes.
     */
    @Getter
    private int remainingHitCount;

    @Getter
    private final double speed;

    @Getter
    private final BulletType type;

    public BulletStatsImpl(BulletType type, double damageModifyingRatio, int remainingHitCount, double speed) {
        this.type = type;
        this.damageModifyingRatio = damageModifyingRatio;
        this.remainingHitCount = remainingHitCount;
        this.speed = speed;
    }

    @Override
    public double getActualDamage(double baseDamage) throws IllegalStateException {
        if (remainingHitCount <= 0) throw new IllegalStateException();
        return baseDamage * damageModifyingRatio;
    }

    @Override
    public boolean shouldDestroyAfterHit() {
        return --remainingHitCount <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BulletStatsImpl) {
            BulletStatsImpl b = (BulletStatsImpl) o;
            return this.damageModifyingRatio == b.damageModifyingRatio &&
                    this.remainingHitCount == b.remainingHitCount;
        }
        return false;
    }
}
