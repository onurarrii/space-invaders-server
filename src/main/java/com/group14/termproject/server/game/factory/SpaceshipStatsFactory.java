package com.group14.termproject.server.game.factory;

import com.group14.termproject.server.game.model.spaceship.stats.SpaceshipStats;
import com.group14.termproject.server.game.model.spaceship.stats.SpaceshipStatsImpl;
import com.group14.termproject.server.game.util.enums.SpaceshipTier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.group14.termproject.server.game.util.GameConstants.Spaceship.Stats;

public class SpaceshipStatsFactory {

    private static SpaceshipStatsFactory instance;

    final Map<SpaceshipTier, Supplier<SpaceshipStats>> factory = new HashMap<>() {{
        put(SpaceshipTier.TIER1, () -> generateSpaceshipStats(SpaceshipTier.TIER1));
        put(SpaceshipTier.TIER2, () -> generateSpaceshipStats(SpaceshipTier.TIER2));
        put(SpaceshipTier.TIER3, () -> generateSpaceshipStats(SpaceshipTier.TIER3));
        put(SpaceshipTier.TIER4, () -> generateSpaceshipStats(SpaceshipTier.TIER4));
    }};

    private SpaceshipStatsFactory() {
    }

    public static SpaceshipStatsFactory getInstance() {
        if (instance == null) {
            instance = new SpaceshipStatsFactory();
        }
        return instance;
    }

    /**
     * Constructs a {@code SpaceshipStats} object.
     *
     * @param tier one of {@code SpaceshipStatsLevel}.
     * @return a newly generated object based on the chosen tier.
     */
    public SpaceshipStats getSpaceshipStats(SpaceshipTier tier) {
        return factory.get(tier).get();
    }

    private SpaceshipStats generateSpaceshipStats(SpaceshipTier tier) {
        Stats stats = new Stats(tier);
        return new SpaceshipStatsImpl(tier, stats.MAX_HEALTH, stats.ARMOR, stats.ATTACK_POWER, stats.ATTACK_SPEED,
                stats.HEALTH_REGENERATION, stats.HEALTH_REGENERATION_SPEED, stats.ENHANCED_BULLET_RANDOM_RATIO,
                stats.MOVEMENT_SPEED);
    }
}
