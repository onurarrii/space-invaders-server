package com.group14.termproject.server.game.model.spaceship.stats;

import com.group14.termproject.server.game.factory.SpaceshipStatsFactory;
import com.group14.termproject.server.game.util.GameConstants;
import com.group14.termproject.server.game.util.enums.SpaceshipTier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpaceshipStatsTests {
    SpaceshipStatsFactory spaceshipStatsFactory;

    private final long TIME_EPSILON = 2;

    private SpaceshipStats generateSpaceshipStats() {
        return spaceshipStatsFactory.getSpaceshipStats(SpaceshipTier.TIER1);
    }

    @Before
    public void setup() {
        this.spaceshipStatsFactory = SpaceshipStatsFactory.getInstance();
    }

    @Test
    public void testUpdateOnFrame() throws InterruptedException {
        SpaceshipStats stats = generateSpaceshipStats();
        double health = 100;
        long healthRegenerationSpeed = 200;
        double healthRegeneration = 100;
        double maxHealth = 1000;
        ReflectionTestUtils.setField(stats, "health", health);
        ReflectionTestUtils.setField(stats, "healthRegenerationSpeed", healthRegenerationSpeed);
        ReflectionTestUtils.setField(stats, "healthRegeneration", healthRegeneration);
        ReflectionTestUtils.setField(stats, "maxHealth", maxHealth);

        long sleepTime = GameConstants.SPACESHIP_BASE_HEALTH_REGENERATION_FREQUENCY / healthRegenerationSpeed + TIME_EPSILON;

        // When it is the first frame then it should regenerate health
        stats.updateOnFrame();
        Assert.assertEquals(health + healthRegeneration, stats.getHealth(), 0.0);

        // When enough time hasn't passed then it should not regenerate health
        stats.updateOnFrame();
        Assert.assertEquals(health + healthRegeneration, stats.getHealth(), 0.0);

        // When enough time has passed to regenerate health then health should be increased
        Thread.sleep(sleepTime);
        stats.updateOnFrame();
        Assert.assertEquals(health + healthRegeneration * 2, stats.getHealth(), 0.0);
    }

    @Test
    public void testTryAttacking() throws InterruptedException {
        SpaceshipStats stats = generateSpaceshipStats();
        long attackSpeed = 200;
        ReflectionTestUtils.setField(stats, "attackSpeed", attackSpeed);

        long sleepTime = GameConstants.SPACESHIP_BASE_BASE_ATTACK_FREQUENCY / attackSpeed + TIME_EPSILON;

        // When it is the first attack then player should be able to attack
        Assert.assertTrue(stats.tryAttacking());

        // When enough time hasn't passed then player should not be able to attack
        Assert.assertFalse(stats.tryAttacking());

        // When enough time has passed then player should be able to attack
        Thread.sleep(sleepTime);
        Assert.assertTrue(stats.tryAttacking());

    }

    @Test
    public void testOnDamageTaken() {
        SpaceshipStats stats = generateSpaceshipStats();
        double armor = 100;
        double health = 1000;
        ReflectionTestUtils.setField(stats, "armor", armor);
        ReflectionTestUtils.setField(stats, "health", health);

        double damage = 250;
        double expectedDamageReduction = Math.pow(Math.max(damage, armor * damage), 0.5);
        double expectedFinalHealth = health - (damage - expectedDamageReduction);

        // When the player has an armor then the armor should emit the 'expectedDamageReduction' amount of damage
        stats.onDamageTaken(damage);
        Assert.assertEquals(expectedFinalHealth, stats.getHealth(), 0.0);

        ReflectionTestUtils.setField(stats, "armor", 0);
        ReflectionTestUtils.setField(stats, "health", health);
        expectedFinalHealth = health - damage;

        // When the player has no armor then the received damage should not be modified
        stats.onDamageTaken(damage);
        Assert.assertEquals(expectedFinalHealth, stats.getHealth(), 0.0);

        damage = Double.MAX_VALUE;
        ReflectionTestUtils.setField(stats, "health", health);

        // When the player takes damage more than their health then health should be 0
        stats.onDamageTaken(damage);
        Assert.assertEquals(0, stats.getHealth(), 0.0);

        damage = 1;
        armor = Double.MAX_VALUE;
        ReflectionTestUtils.setField(stats, "armor", armor);
        ReflectionTestUtils.setField(stats, "health", health);

        // When the armor is too big then no damage should be taken
        stats.onDamageTaken(damage);
        Assert.assertEquals(health, stats.getHealth(), 0.0);
    }

    @Test
    public void testIllegalConstructorParameter() {
        List<Integer> illegalEnhancedBulletRandomRatios = Arrays.asList(-1, 3);
        for (Integer ratio : illegalEnhancedBulletRandomRatios) {
            Assert.assertThrows(IllegalArgumentException.class, () -> new SpaceshipStatsImpl(SpaceshipTier.TIER1, 1000, 20,
                    50, 250, 0, 250,
                    ratio, 1));
        }

    }
}
