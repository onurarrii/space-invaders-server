package com.group14.termproject.server.game.model.spaceship.stats;

import com.group14.termproject.server.game.util.GameConstants;
import com.group14.termproject.server.game.util.TimeUtil;
import com.group14.termproject.server.game.util.enums.SpaceshipTier;
import lombok.Getter;

import java.util.Random;

public class SpaceshipStatsImpl implements SpaceshipStats {
    @Getter
    private final SpaceshipTier tier;
    @Getter
    private final double maxHealth;
    @Getter
    private double health;
    @Getter
    private final double armor;
    @Getter
    private final double attackPower;
    @Getter
    private final long attackSpeed;
    @Getter
    private final double healthRegeneration;
    @Getter
    private final long healthRegenerationSpeed;
    @Getter
    private double enhancedBulletRandomRatio;
    @Getter
    private final double movementSpeed;

    // All time variables are in milliseconds
    private long lastAttackTime = 0;
    private long lastHealthRegenerationTime = 0;

    public SpaceshipStatsImpl(SpaceshipTier tier, double maxHealth, double armor, double attackPower,
                              long attackSpeed, double healthRegeneration, long healthRegenerationSpeed,
                              double enhancedBulletRandomRatio, double movementSpeed)
            throws IllegalArgumentException {
        this.tier = tier;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.armor = armor;
        this.attackPower = attackPower;
        this.attackSpeed = attackSpeed;
        this.healthRegeneration = healthRegeneration;
        this.healthRegenerationSpeed = healthRegenerationSpeed;
        this.movementSpeed = movementSpeed;
        if (enhancedBulletRandomRatio < 0 || enhancedBulletRandomRatio > 1) {
            throw new IllegalArgumentException();
        }
        this.enhancedBulletRandomRatio = enhancedBulletRandomRatio;
    }

    @Override
    public void updateOnFrame() {
        this.tryToRegenerateHealth();
    }

    private void tryToRegenerateHealth() {
        boolean isEnoughTimePassed = TimeUtil.isEnoughTimePassed(lastHealthRegenerationTime, calculateHealthRegenerationFrequency());
        if (isEnoughTimePassed) {
            this.lastHealthRegenerationTime = TimeUtil.getCurrentTimeInMs();
            regenerateHealth();
        }
    }

    private void regenerateHealth() {
        double calculatedHealth = health + healthRegeneration;
        this.health = Math.min(calculatedHealth, maxHealth);
    }

    @Override
    public boolean tryAttacking() {
        long attackFrequency = calculateAttackFrequency();
        boolean isEnoughTimePassed = TimeUtil.isEnoughTimePassed(lastAttackTime, attackFrequency);
        if (isEnoughTimePassed) {
            this.lastAttackTime = TimeUtil.getCurrentTimeInMs();
        }
        return isEnoughTimePassed;
    }

    private long calculateAttackFrequency() {
        return GameConstants.SPACESHIP_BASE_BASE_ATTACK_FREQUENCY / this.attackSpeed;
    }

    private long calculateHealthRegenerationFrequency() {
        return GameConstants.SPACESHIP_BASE_HEALTH_REGENERATION_FREQUENCY / this.healthRegenerationSpeed;
    }

    @Override
    public boolean canGenerateEnhancedBullet() {
        return enhancedBulletRandomRatio > new Random().nextDouble();
    }

    @Override
    public void onDamageTaken(double damage) {
        double actualDamage = Math.max(0, damage - this.calculateDamageReduction(damage));
        this.health = Math.max(0, health - actualDamage);
    }

    private double calculateDamageReduction(double damage) {
        return Math.pow(this.armor * damage, 0.5);
    }

    @Override
    public boolean isDead() {
        return this.health <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SpaceshipStatsImpl) {
            SpaceshipStatsImpl s = (SpaceshipStatsImpl) o;
            return this.maxHealth == s.maxHealth && this.health == s.health && this.armor == s.armor &&
                    this.attackPower == s.attackPower && this.attackSpeed == s.attackSpeed &&
                    this.healthRegeneration == s.healthRegeneration &&
                    this.healthRegenerationSpeed == s.healthRegenerationSpeed &&
                    this.movementSpeed == s.movementSpeed;
        }
        return false;
    }
}
