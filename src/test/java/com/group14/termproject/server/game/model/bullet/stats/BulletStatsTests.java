package com.group14.termproject.server.game.model.bullet.stats;

import com.group14.termproject.server.game.factory.BulletStatsFactory;
import com.group14.termproject.server.game.util.enums.BulletType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BulletStatsTests {
    private BulletStatsFactory bulletStatsFactory;

    @Before
    public void setup() {
        bulletStatsFactory = BulletStatsFactory.getInstance();
    }

    @Test
    public void testBasicBulletActualDamage() {
        BulletStats bs = bulletStatsFactory.getBulletStats(BulletType.BASIC);
        double randomDamage = 20;
        // Basic bullets do not get enhanced damage
        Assert.assertEquals(randomDamage, bs.getActualDamage(randomDamage), 0.0);
    }

    @Test
    public void testEnhancedBulletActualDamage() {
        BulletStats bulletStats = bulletStatsFactory.getBulletStats(BulletType.ENHANCED);
        double randomDamage = 50;
        double randomDamageModifyRatio = 1.2;
        ReflectionTestUtils.setField(bulletStats, "damageModifyingRatio", randomDamageModifyRatio);
        double expected = randomDamage * randomDamageModifyRatio;
        Assert.assertEquals(expected, bulletStats.getActualDamage(randomDamage), 0.0);
    }

    @Test
    public void testActualDamageWithZeroHitCount() {
        BulletStats bulletStats = bulletStatsFactory.getBulletStats(BulletType.ENHANCED);
        ReflectionTestUtils.setField(bulletStats, "remainingHitCount", 0);
        Assert.assertThrows(IllegalStateException.class, () -> bulletStats.getActualDamage(5));
    }

    private void testShouldDestroyAfterHit(BulletStats bulletStats, int remainingHitCount) {
        ReflectionTestUtils.setField(bulletStats, "remainingHitCount", remainingHitCount);
        for (int hitCount = 0; hitCount < remainingHitCount - 1; hitCount++) {
            Assert.assertFalse(bulletStats.shouldDestroyAfterHit());
        }
        Assert.assertTrue(bulletStats.shouldDestroyAfterHit());
    }

    @Test
    public void testShouldDestroyAfterHit() {
        BulletStats bulletStats = bulletStatsFactory.getBulletStats(BulletType.ENHANCED);
        // When the bullet can only hit once
        testShouldDestroyAfterHit(bulletStats, 1);
        // When the bullet can hit multiple times
        testShouldDestroyAfterHit(bulletStats, 10);

    }
}
