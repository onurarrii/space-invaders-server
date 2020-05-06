package com.group14.termproject.server.game.factory;

import com.group14.termproject.server.game.model.bullet.stats.BulletStats;
import com.group14.termproject.server.game.model.bullet.stats.BulletStatsImpl;
import com.group14.termproject.server.game.util.enums.BulletType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.group14.termproject.server.game.util.GameConstants.Bullet.Stats;
public class BulletStatsFactory {

    private static BulletStatsFactory instance;

    private final Map<BulletType, Supplier<BulletStats>> stats = new HashMap<>() {{
        put(BulletType.BASIC, () -> generateBulletStats(BulletType.BASIC));
        put(BulletType.ENHANCED, () -> generateBulletStats(BulletType.ENHANCED));
    }};

    private BulletStatsFactory() {

    }

    public static BulletStatsFactory getInstance() {
        if (instance == null) {
            instance = new BulletStatsFactory();
        }
        return instance;
    }

    /**
     * Constructs a {@code BulletStats} object.
     *
     * @param type one of {@code BulletType}.
     * @return a newly generated object based on the chosen type.
     */
    public BulletStats getBulletStats(BulletType type) {
        return stats.get(type).get();
    }

    private BulletStats generateBulletStats(BulletType type) {
        Stats stats = new Stats(type);
        return new BulletStatsImpl(type, stats.DAMAGE_MODIFY_RATIO, stats.HIT_COUNT, stats.BULLET_SPEED);
    }
}
