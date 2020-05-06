package com.group14.termproject.server.game.management;

import com.group14.termproject.server.game.model.GameObject;
import com.group14.termproject.server.game.model.bullet.Bullet;
import com.group14.termproject.server.game.model.spaceship.PlayerSpaceship;
import com.group14.termproject.server.game.model.spaceship.Spaceship;
import com.group14.termproject.server.game.util.GameConstants;
import com.group14.termproject.server.game.util.LevelUtil;
import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.game.util.enums.Level;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@Setter
public class GameManager implements Runnable {

    private Consumer<GameManager> onGameCompletedCallback;
    private Level level;
    private long userId;
    private boolean gameCompleted = false;
    private boolean gameStarted = false;
    private double cumulativeScore = 0;
    /**
     * Stores all game objects, which are not destroyed, in the current level.
     * Note that, the objects it stores are equal to the union of {@code spaceships, bullets}.
     */
    private List<GameObject> gameObjects = new ArrayList<>();
    private List<Spaceship> spaceships = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private PlayerSpaceship playerSpaceship;

    private List<Bullet> generatedBullets = new ArrayList<>();

    public GameManager(long userId) {
        this.userId = userId;
        this.level = Level.LEVEL_1;
        this.loadCurrentLevel();
    }

    @Override
    public void run() {
        gameStarted = true;
        while (!gameCompleted) {
            try {
                Thread.sleep((long) GameConstants.TIME_BETWEEN_FRAMES);
                updateOnFrame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void bindOnGameCompletedCallback(Consumer<GameManager> callback) {
        this.onGameCompletedCallback = callback;
    }

    public void cheat() throws IllegalAccessException {
        if (!gameStarted)
            throw new IllegalAccessException("To be able to cheat, the game must be started.");
        onLevelCompleted();
    }

    public void onMousePositionChange(Vector2D position) {
        this.playerSpaceship.onMousePositionChange(position);
    }

    private void updateOnFrame() {
        for (GameObject gameObject : gameObjects) {
            gameObject.updateOnFrame();
        }
        for (Bullet bullet : bullets) {
            for (Spaceship spaceship : spaceships) {
                if (bullet.isDestroyed()) break;
                if (!spaceship.isDestroyed() && bullet.isColliding(spaceship)) {
                    bullet.onCollision(spaceship);
                }
            }
        }
        removeDestroyedObjects();
        if (isPlayerDied()) {
            onGameCompleted();
        } else if (isLevelCompleted()) {
            onLevelCompleted();
        }
        addGeneratedBulletsToLists();
    }

    private boolean isLevelCompleted() {
        return this.spaceships.size() <= 1;
    }

    private boolean isPlayerDied() {
        return playerSpaceship.isDestroyed();
    }

    private void onLevelCompleted() {
        this.cumulativeScore = this.playerSpaceship.getScore();
        level = LevelUtil.next(level);
        boolean doesNextLevelExist = level != null;
        if (doesNextLevelExist)
            loadCurrentLevel();
        else {
            onGameCompleted();
        }
    }

    public void onGameCompleted() {
        this.gameCompleted = true;
        this.onGameCompletedCallback.accept(this);
    }

    private void loadCurrentLevel() {
        this.spaceships = LevelUtil.getEnemySpaceships(level);
        this.playerSpaceship = LevelUtil.getPlayerSpaceship(level, userId);
        this.playerSpaceship.setScore(this.cumulativeScore);
        // Add player spaceship to spaceships as it is a spaceship as well
        this.spaceships.add(playerSpaceship);
        // Copy all spaceships to gameObjects as every spaceship is a game object.
        this.gameObjects = new ArrayList<>(spaceships);
        this.bullets.clear();
        this.bindBulletGenerationCallbacks();
    }

    private void removeDestroyedObjects() {
        class DestroyedObjectCleaner<T extends GameObject> {
            List<T> clean(List<T> list) {
                return list.stream()
                        .filter(object -> !object.isDestroyed())
                        .collect(Collectors.toList());
            }
        }
        this.gameObjects = new DestroyedObjectCleaner<>().clean(this.gameObjects);
        this.bullets = new DestroyedObjectCleaner<Bullet>().clean(this.bullets);
        this.spaceships = new DestroyedObjectCleaner<Spaceship>().clean(this.spaceships);
    }

    private void bindBulletGenerationCallbacks() {
        for (Spaceship spaceship : spaceships) {
            spaceship.bindBulletGenerationCallback(bullet -> generatedBullets.add(bullet));
        }
    }

    private void addGeneratedBulletsToLists() {
        for (Bullet bullet : generatedBullets) {
            bullets.add(bullet);
            gameObjects.add(bullet);
        }
        generatedBullets.clear();
    }
}
