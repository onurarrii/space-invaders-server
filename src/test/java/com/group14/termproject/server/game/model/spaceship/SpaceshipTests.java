package com.group14.termproject.server.game.model.spaceship;

import com.group14.termproject.server.AuthTestUtil;
import com.group14.termproject.server.game.factory.GameObjectFactory;
import com.group14.termproject.server.game.factory.SpaceshipStatsFactory;
import com.group14.termproject.server.game.model.bullet.Bullet;
import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.game.util.enums.SpaceshipTier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SpaceshipTests {
    GameObjectFactory gameObjectFactory;
    @Autowired
    AuthTestUtil authTestUtil;
    long dummyUserId;

    @Before
    public void setup() {
        this.gameObjectFactory = GameObjectFactory.getInstance();
        this.dummyUserId = authTestUtil.generateRegisteredUser().getId();
    }

    @Test
    public void testSpaceshipInitialization() {
        SpaceshipTier[] possibleTierValues = SpaceshipTier.values();
        for (SpaceshipTier value : possibleTierValues) {
            Assert.assertNotNull(gameObjectFactory.getEnemySpaceship(value));
            Assert.assertNotNull(gameObjectFactory.getPlayerSpaceship(dummyUserId, value));
        }
    }

    @Test
    public void testIsEnemy() {
        Spaceship enemySpaceship = gameObjectFactory.getEnemySpaceship(SpaceshipTier.TIER1);
        Spaceship playerSpaceship = gameObjectFactory.getPlayerSpaceship(dummyUserId, SpaceshipTier.TIER1);
        Assert.assertTrue(enemySpaceship.isEnemy(playerSpaceship));
        Assert.assertTrue(playerSpaceship.isEnemy(enemySpaceship));
    }

    @Test
    public void testBulletGenerationOnFire() {
        Spaceship spaceship = gameObjectFactory.getEnemySpaceship(SpaceshipTier.TIER1);
        Bullet bullet = spaceship.fire();
        Assert.assertNotEquals(Vector2D.zero(), bullet.getVelocity());
        // Their rotation should be in the same way.
        Assert.assertEquals(spaceship.getRotation(), bullet.getRotation());
        // Also, the bullet should be headed to the spaceship's rotation.
        Assert.assertEquals(spaceship.getRotation().normalized(), bullet.getVelocity().normalized());
    }

    @Test
    public void testOnDamageDealt() {
        Spaceship playerSpaceship = gameObjectFactory.getPlayerSpaceship(dummyUserId, SpaceshipTier.TIER1);
        Spaceship enemy = gameObjectFactory.getEnemySpaceship(SpaceshipTier.TIER1);
        // Enemy should be killed as damage is very high.
        enemy.onDamageDealt(playerSpaceship, 100000);
        Assert.assertTrue(enemy.isDestroyed());
        // Enemy should still be alive after low damage.
        enemy = gameObjectFactory.getEnemySpaceship(SpaceshipTier.TIER4);
        enemy.onDamageDealt(playerSpaceship, 0.1);
        Assert.assertFalse(enemy.isDestroyed());
    }

    @Test
    public void testOnKillEnemy() {
        PlayerSpaceship player = gameObjectFactory.getPlayerSpaceship(this.dummyUserId, SpaceshipTier.TIER1);
        Spaceship firstEnemy = gameObjectFactory.getEnemySpaceship(SpaceshipTier.TIER1);
        player.onEnemyKilled(firstEnemy);
        Assert.assertEquals(firstEnemy.getScoreValue(), player.getScore(), 0.0);
        Spaceship secondEnemy = gameObjectFactory.getEnemySpaceship(SpaceshipTier.TIER2);
        // Killed the second spaceship
        player.onEnemyKilled(secondEnemy);
        double expectedFinalScore = firstEnemy.getScoreValue() + secondEnemy.getScoreValue();
        Assert.assertEquals(expectedFinalScore, player.getScore(), 0.0);
    }

    @Test
    public void testBindBulletGenerationCallback() {
        PlayerSpaceship player = gameObjectFactory.getPlayerSpaceship(this.dummyUserId, SpaceshipTier.TIER1);
        Consumer<Bullet> callback = Assert::assertNotNull;
        player.bindBulletGenerationCallback(callback);
        player.updateOnFrame();
    }

    @Test
    public void testHealthInitByTier() {
        SpaceshipTier tier = SpaceshipTier.TIER1;
        Spaceship player = gameObjectFactory.getEnemySpaceship(tier);
        Assert.assertEquals(tier, player.getTier());
        Assert.assertEquals(SpaceshipStatsFactory.getInstance().getSpaceshipStats(tier).getHealth(),
                player.getHealth(), 0.0);
        Assert.assertEquals(SpaceshipStatsFactory.getInstance().getSpaceshipStats(tier).getMaxHealth(),
                player.getMaxHealth(), 0.0);
    }


}
