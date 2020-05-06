package com.group14.termproject.server.game.util;

import com.group14.termproject.server.game.util.enums.BulletType;
import com.group14.termproject.server.game.util.enums.SpaceshipTier;

public final class GameConstants {

    public static final int GRID_WIDTH = 800;
    public static final int GRID_HEIGHT = 600;

    public static final int FPS = 60;
    public static final double TIME_BETWEEN_FRAMES = 1000.0 / FPS;

    public static final double BULLET_RIGID_BODY_HEIGHT = 25;
    public static final double BULLET_RIGID_BODY_WIDTH = 25;

    public static final double SPACESHIP_RIGID_BODY_HEIGHT = 75;
    public static final double SPACESHIP_RIGID_BODY_WIDTH = 75;

    public static final long SPACESHIP_BASE_HEALTH_REGENERATION_FREQUENCY = 100000;
    public static final long SPACESHIP_BASE_BASE_ATTACK_FREQUENCY = 100000;

    private GameConstants() {
    }

    public static final class Bullet {
        private Bullet() {
        }

        public static class Stats {
            public double DAMAGE_MODIFY_RATIO;
            public int HIT_COUNT;
            public double BULLET_SPEED;

            public Stats(BulletType type) {
                switch (type) {
                    case BASIC:
                        DAMAGE_MODIFY_RATIO = 1.0;
                        HIT_COUNT = 1;
                        BULLET_SPEED = 2.0;
                        break;
                    case ENHANCED:
                        DAMAGE_MODIFY_RATIO = 1.2;
                        HIT_COUNT = 3;
                        BULLET_SPEED = 3.0;
                        break;
                }
            }
        }
    }

    public static final class Spaceship {

        private Spaceship() {
        }

        public static class Stats {
            public double MAX_HEALTH;
            public double ARMOR;
            public double ATTACK_POWER;
            public long ATTACK_SPEED;
            public double HEALTH_REGENERATION;
            public long HEALTH_REGENERATION_SPEED;
            public double ENHANCED_BULLET_RANDOM_RATIO;
            public double MOVEMENT_SPEED;
            public double WORTH_SCORE;

            public Stats(SpaceshipTier tier) {
                switch (tier) {
                    case TIER1:
                        MAX_HEALTH = 200;
                        ARMOR = 2;
                        ATTACK_POWER = 80;
                        ATTACK_SPEED = 80;
                        HEALTH_REGENERATION = 0;
                        HEALTH_REGENERATION_SPEED = 1;
                        ENHANCED_BULLET_RANDOM_RATIO = 0;
                        MOVEMENT_SPEED = 1;
                        WORTH_SCORE = 10;
                        break;
                    case TIER2:
                        MAX_HEALTH = 800;
                        ARMOR = 10;
                        ATTACK_POWER = 85;
                        ATTACK_SPEED = 70;
                        HEALTH_REGENERATION = 1;
                        HEALTH_REGENERATION_SPEED = 50;
                        ENHANCED_BULLET_RANDOM_RATIO = 0.1;
                        MOVEMENT_SPEED = 2;
                        WORTH_SCORE = 20;
                        break;
                    case TIER3:
                        MAX_HEALTH = 1400;
                        ARMOR = 50;
                        ATTACK_POWER = 200;
                        ATTACK_SPEED = 100;
                        HEALTH_REGENERATION = 5;
                        HEALTH_REGENERATION_SPEED = 100;
                        ENHANCED_BULLET_RANDOM_RATIO = 0.15;
                        MOVEMENT_SPEED = 4.5;
                        WORTH_SCORE = 30;
                        break;
                    case TIER4:
                        MAX_HEALTH = 2500;
                        ARMOR = 60;
                        ATTACK_POWER = 250;
                        ATTACK_SPEED = 250;
                        HEALTH_REGENERATION = 20;
                        HEALTH_REGENERATION_SPEED = 140;
                        ENHANCED_BULLET_RANDOM_RATIO = 0.25;
                        MOVEMENT_SPEED = 5.5;
                        WORTH_SCORE = 40;
                        break;
                }
            }
        }
    }
}
