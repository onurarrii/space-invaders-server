package com.group14.termproject.server.game.util;

import com.group14.termproject.server.game.model.spaceship.PlayerSpaceship;
import com.group14.termproject.server.game.model.spaceship.Spaceship;
import com.group14.termproject.server.game.util.enums.Level;

import java.util.List;

public final class LevelUtil {
    private static final Level[] levels = Level.values();

    private LevelUtil() {
    }

    public static Level next(Level level) {
        if (isLastLevel(level))
            return null;
        return levels[level.ordinal() + 1];
    }

    public static boolean isLastLevel(Level level) {
        return level.ordinal() == levels.length - 1;
    }

    public static List<Spaceship> getEnemySpaceships(Level level) {
        return LevelGenerator.getEnemySpaceships(level);
    }

    public static PlayerSpaceship getPlayerSpaceship(Level level, long userId) {
        return LevelGenerator.getPlayerSpaceship(level, userId);
    }
}
