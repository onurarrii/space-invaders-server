package com.group14.termproject.server.game.model.bullet;

import com.group14.termproject.server.game.model.GameObjectImpl;
import com.group14.termproject.server.game.model.bullet.stats.BulletStats;
import com.group14.termproject.server.game.model.spaceship.Spaceship;
import com.group14.termproject.server.game.util.GameConstants;
import com.group14.termproject.server.game.util.Rectangle;
import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.game.util.enums.BulletType;
import com.group14.termproject.server.game.util.enums.SpaceshipType;

public class BulletImpl extends GameObjectImpl implements Bullet {

    final BulletStats stats;
    final Spaceship generatedBy;

    public BulletImpl(Rectangle rigidBody, Spaceship generatedBy, BulletStats stats) {
        super(rigidBody, generatedBy.getRotation().normalized());
        this.generatedBy = generatedBy;
        this.stats = stats;
        this.velocity = rotation.getMultiplied(stats.getSpeed());
    }

    @Override
    public void updateOnFrame() {
        if (!isInTheGrid()) {
            destroyed = true;
            return;
        }
        super.updateOnFrame();
    }

    @Override
    public void onCollision(Spaceship spaceship) {
        if (!generatedBy.isEnemy(spaceship)) return;
        double actualDamage = stats.getActualDamage(generatedBy.getDamage());
        spaceship.onDamageDealt(this.generatedBy, actualDamage);
        if (stats.shouldDestroyAfterHit()) destroyed = true;
    }

    @Override
    public BulletType getType() {
        return stats.getType();
    }

    @Override
    public SpaceshipType getOwnerType() {
        return generatedBy.getSpaceshipType();
    }

    private boolean isInTheGrid() {
        Vector2D position = getPosition();
        boolean horizontallyValid = position.x >= 0 && position.x < GameConstants.GRID_WIDTH;
        boolean verticallyValid = position.y >= 0 && position.y < GameConstants.GRID_HEIGHT;
        return horizontallyValid && verticallyValid;
    }
}
