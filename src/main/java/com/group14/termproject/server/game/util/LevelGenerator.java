package com.group14.termproject.server.game.util;

import com.group14.termproject.server.game.factory.GameObjectFactory;
import com.group14.termproject.server.game.model.spaceship.PlayerSpaceship;
import com.group14.termproject.server.game.model.spaceship.Spaceship;
import com.group14.termproject.server.game.util.enums.Level;
import com.group14.termproject.server.game.util.enums.SpaceshipTier;
import com.group14.termproject.server.model.LevelSpaceship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class LevelGenerator {

    private static final List<LevelSpaceship> level1Enemies = new ArrayList<>() {{
        // Back row Tier 1 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(100, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(250, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(400, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(550, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(700, 100)));

        // Front row Tier 1 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(175, 200)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(325, 200)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(475, 200)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(625, 200)));
    }};

    private static final List<LevelSpaceship> level2Enemies = new ArrayList<>() {{
        // Back row, Tier 2 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(100, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(300, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(500, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(700, 100)));

        // Protected Tier 2 spaceships behind Tier 1
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(200, 200)));
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(400, 200)));
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(600, 200)));

        // Front row Tier 1 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(200, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(400, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(600, 300)));
    }};

    private static final List<LevelSpaceship> level3Enemies = new ArrayList<>() {{

        // Back row Tier 3 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(100, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(700, 100)));

        // Back row Tier 1 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(200, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(300, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(400, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(500, 100)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(600, 100)));

        // Middle row Tier 3 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(400, 200)));

        // Middle row Tier 2 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(200, 200)));
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(600, 200)));

        // Front row Tier 1 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(75, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(150, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(225, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(300, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(500, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(575, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(650, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(725, 300)));
    }};

    private static final List<LevelSpaceship> level4Enemies = new ArrayList<>() {{
        // Back row Tier 3 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(100, 50)));
        add(new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(700, 50)));
        add(new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(350, 75)));
        add(new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(450, 75)));

        // Middle row Tier 3 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(250, 150)));
        add(new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(550, 150)));

        // Front row Tier 3 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(400, 250)));

        // Front row Tier 2 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(125, 250)));
        add(new LevelSpaceship(SpaceshipTier.TIER2, new Vector2D(675, 250)));

        // Front row Tier 1 spaceships
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(250, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(400, 300)));
        add(new LevelSpaceship(SpaceshipTier.TIER1, new Vector2D(550, 300)));
    }};

    private static final List<LevelSpaceship> level5Enemies = new ArrayList<>();

    private static final LevelSpaceship level1Player = new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(400, 500));
    private static final LevelSpaceship level2Player = new LevelSpaceship(SpaceshipTier.TIER3, new Vector2D(400, 500));
    private static final LevelSpaceship level3Player = new LevelSpaceship(SpaceshipTier.TIER4, new Vector2D(400, 500));
    private static final LevelSpaceship level4Player = new LevelSpaceship(SpaceshipTier.TIER4, new Vector2D(400, 500));
    private static final LevelSpaceship level5Player = new LevelSpaceship(SpaceshipTier.TIER4, new Vector2D(400, 500));

    private static final Map<Level, List<LevelSpaceship>> enemySpaceships = new HashMap<>() {{
        put(Level.LEVEL_1, level1Enemies);
        put(Level.LEVEL_2, level2Enemies);
        put(Level.LEVEL_3, level3Enemies);
        put(Level.LEVEL_4, level4Enemies);
        put(Level.LEVEL_5, level5Enemies);
    }};

    public static final Map<Level, LevelSpaceship> playerSpaceships = new HashMap<>() {{
        put(Level.LEVEL_1, level1Player);
        put(Level.LEVEL_2, level2Player);
        put(Level.LEVEL_3, level3Player);
        put(Level.LEVEL_4, level4Player);
        put(Level.LEVEL_5, level5Player);
    }};

    private LevelGenerator() {
    }

    public static List<Spaceship> getEnemySpaceships(Level level) {
        List<LevelSpaceship> levelSpaceships = enemySpaceships.get(level);
        return levelSpaceships
                .stream()
                .map(LevelGenerator::enemySpaceshipConverter)
                .collect(Collectors.toList());
    }

    public static PlayerSpaceship getPlayerSpaceship(Level level, double userId) {
        LevelSpaceship levelSpaceship = playerSpaceships.get(level);
        return playerSpaceshipConverter(levelSpaceship, userId);
    }

    private static Spaceship enemySpaceshipConverter(LevelSpaceship levelSpaceship) {
        Spaceship spaceship = GameObjectFactory.getInstance().getEnemySpaceship(levelSpaceship.getTier());
        spaceship.getRigidBody().reposition(levelSpaceship.getPosition());
        return spaceship;
    }

    private static PlayerSpaceship playerSpaceshipConverter(LevelSpaceship levelSpaceship, double userId) {
        PlayerSpaceship spaceship = GameObjectFactory.getInstance().getPlayerSpaceship(userId, levelSpaceship.getTier());
        spaceship.getRigidBody().reposition(levelSpaceship.getPosition());
        return spaceship;
    }
}
