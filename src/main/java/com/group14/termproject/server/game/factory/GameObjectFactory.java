package com.group14.termproject.server.game.factory;

import com.group14.termproject.server.game.model.bullet.Bullet;
import com.group14.termproject.server.game.model.bullet.BulletImpl;
import com.group14.termproject.server.game.model.bullet.stats.BulletStats;
import com.group14.termproject.server.game.model.spaceship.*;
import com.group14.termproject.server.game.model.spaceship.stats.SpaceshipStats;
import com.group14.termproject.server.game.util.GameConstants;
import com.group14.termproject.server.game.util.Rectangle;
import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.game.util.enums.BulletType;
import com.group14.termproject.server.game.util.enums.SpaceshipTier;
import com.group14.termproject.server.game.util.enums.SpaceshipType;

public class GameObjectFactory {

    private static GameObjectFactory instance;

    final BulletStatsFactory bulletStatsFactory = BulletStatsFactory.getInstance();
    final SpaceshipStatsFactory spaceshipStatsFactory = SpaceshipStatsFactory.getInstance();

    private GameObjectFactory() {
    }

    public static GameObjectFactory getInstance() {
        if (instance == null) {
            instance = new GameObjectFactory();
        }
        return instance;
    }

    public Bullet getBullet(Spaceship owner, BulletType type) {
        Rectangle rigidBody = generateBulletRigidBody(owner);
        BulletStats bulletStats = bulletStatsFactory.getBulletStats(type);
        return new BulletImpl(rigidBody, owner, bulletStats);
    }

    public PlayerSpaceship getPlayerSpaceship(double userId, SpaceshipTier tier) {
        Rectangle defaultRigidBody = generateSpaceshipRigidBody();
        Vector2D defaultRotation = Vector2D.up();
        SpaceshipStats spaceshipStats = spaceshipStatsFactory.getSpaceshipStats(tier);
        return new PlayerSpaceshipImpl(defaultRigidBody, defaultRotation, spaceshipStats, SpaceshipType.PLAYER, userId);
    }

    public Spaceship getEnemySpaceship(SpaceshipTier tier) {
        Rectangle defaultRigidBody = generateSpaceshipRigidBody();
        Vector2D defaultRotation = Vector2D.down();
        SpaceshipStats spaceshipStats = spaceshipStatsFactory.getSpaceshipStats(tier);
        return new SpaceshipImpl(defaultRigidBody, defaultRotation, spaceshipStats, SpaceshipType.ENEMY);
    }

    private Rectangle generateBulletRigidBody(Spaceship owner) {
        return new Rectangle(GameConstants.BULLET_RIGID_BODY_WIDTH, GameConstants.BULLET_RIGID_BODY_HEIGHT,
                owner.getPosition());
    }

    private Rectangle generateSpaceshipRigidBody() {
        Vector2D defaultPosition = Vector2D.zero();
        return new Rectangle(GameConstants.SPACESHIP_RIGID_BODY_WIDTH, GameConstants.SPACESHIP_RIGID_BODY_HEIGHT,
                defaultPosition);
    }
}
