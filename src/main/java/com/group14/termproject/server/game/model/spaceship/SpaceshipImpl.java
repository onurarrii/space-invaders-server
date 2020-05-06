package com.group14.termproject.server.game.model.spaceship;

import com.group14.termproject.server.game.factory.GameObjectFactory;
import com.group14.termproject.server.game.model.GameObjectImpl;
import com.group14.termproject.server.game.model.bullet.Bullet;
import com.group14.termproject.server.game.model.spaceship.stats.SpaceshipStats;
import com.group14.termproject.server.game.util.Rectangle;
import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.game.util.GameConstants;
import com.group14.termproject.server.game.util.enums.BulletType;
import com.group14.termproject.server.game.util.enums.SpaceshipTier;
import com.group14.termproject.server.game.util.enums.SpaceshipType;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
public class SpaceshipImpl extends GameObjectImpl implements Spaceship {

    SpaceshipStats spaceshipStats;
    SpaceshipType spaceshipType;
    Consumer<Bullet> onBulletGenerationCallback;

    public SpaceshipImpl(Rectangle rigidBody, Vector2D rotation, SpaceshipStats spaceshipStats,
                         SpaceshipType spaceshipType) {
        super(rigidBody, rotation);
        this.spaceshipStats = spaceshipStats;
        this.spaceshipType = spaceshipType;
    }

    @Override
    public void updateOnFrame() {
        boolean isAlive = !this.spaceshipStats.isDead() && !destroyed;
        this.spaceshipStats.updateOnFrame();
        if (isAlive && this.spaceshipStats.tryAttacking()) {
            Bullet bullet = fire();
            invokeBulletGenerationCallback(bullet);
        }
        super.updateOnFrame();
    }

    @Override
    public Bullet fire() {
        BulletType bulletType = spaceshipStats.canGenerateEnhancedBullet() ?
                BulletType.ENHANCED : BulletType.BASIC;
        return GameObjectFactory.getInstance().getBullet(this, bulletType);
    }

    @Override
    public void bindBulletGenerationCallback(Consumer<Bullet> callback) {
        this.onBulletGenerationCallback = callback;
    }

    private void invokeBulletGenerationCallback(Bullet bullet) {
        if (onBulletGenerationCallback != null)
            onBulletGenerationCallback.accept(bullet);
    }

    @Override
    public void onDamageDealt(Spaceship dealer, double damage) {
        this.spaceshipStats.onDamageTaken(damage);
        if (this.spaceshipStats.isDead()) {
            destroyed = true;
            dealer.onEnemyKilled(this);
        }
    }

    @Override
    public boolean isEnemy(Spaceship other) {
        return !spaceshipType.equals(other.getSpaceshipType());
    }

    @Override
    public double getDamage() {
        return this.spaceshipStats.getAttackPower();
    }

    @Override
    public SpaceshipTier getTier() {
        return this.spaceshipStats.getTier();
    }

    @Override
    public double getMaxHealth() {
        return this.spaceshipStats.getMaxHealth();
    }

    @Override
    public double getHealth() {
        return this.spaceshipStats.getHealth();
    }

    @Override
    public double getScoreValue() {
        GameConstants.Spaceship.Stats stats = new GameConstants.Spaceship.Stats(spaceshipStats.getTier());
        return stats.WORTH_SCORE;
    }
}
