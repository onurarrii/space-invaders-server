package com.group14.termproject.server.game.model;

import com.group14.termproject.server.AuthTestUtil;
import com.group14.termproject.server.game.factory.GameObjectFactory;
import com.group14.termproject.server.game.model.spaceship.PlayerSpaceship;
import com.group14.termproject.server.game.model.spaceship.Spaceship;
import com.group14.termproject.server.game.model.spaceship.stats.SpaceshipStats;
import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.game.util.enums.SpaceshipTier;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GameObjectTests {

    @Autowired
    AuthTestUtil authTestUtil;

    private GameObject generateGameObject() {
        return GameObjectFactory.getInstance().getEnemySpaceship(SpaceshipTier.TIER1);
    }

    @Test
    public void testDefaultPosition() {
        GameObject gameObject = generateGameObject();
        Assert.assertEquals(Vector2D.zero(), gameObject.getPosition());
    }

    @Test
    public void testIdUniqueness() {
        int testCount = 1000;
        Set<String> generatedIds = new HashSet<>();
        for (int i = 0; i < testCount; i++) {
            String id = generateGameObject().getId();
            Assert.assertFalse(generatedIds.contains(id));
            generatedIds.add(id);
        }
    }

    @Test
    public void testUpdateOnFrame() {
        GameObject gameObject = generateGameObject();
        ReflectionTestUtils.setField(gameObject, "velocity", Vector2D.up());
        // An object with velocity should move each frame
        gameObject.updateOnFrame();
        Vector2D expected = Vector2D.up();
        Assert.assertEquals(expected, gameObject.getPosition());
        // Stop the object. It should not change its position on next frame.
        ReflectionTestUtils.setField(gameObject, "velocity", Vector2D.zero());
        gameObject.updateOnFrame();
        Assert.assertEquals(expected, gameObject.getPosition());
    }

    @Test
    public void testUpdateOnSecond() throws InterruptedException {
        GameObject gameObject = generateGameObject();
        // Since its initial implementation is empty, check if it throws an error.
        gameObject.updateOnSecond();
        Thread.sleep(1000);
        gameObject.updateOnSecond();
    }

    @Test
    public void testSingleMove() {
        // Vertical movement
        GameObject g1 = generateGameObject();
        ReflectionTestUtils.setField(g1, "velocity", Vector2D.up());
        ReflectionTestUtils.invokeMethod(g1, "move");
        Assert.assertEquals(Vector2D.up(), g1.getPosition());

        // Horizontal movement
        GameObject g2 = generateGameObject();
        ReflectionTestUtils.setField(g2, "velocity", new Vector2D(1, 0));
        ReflectionTestUtils.invokeMethod(g2, "move");
        Assert.assertEquals(new Vector2D(1, 0), g2.getPosition());

        // Movement in both directions
        GameObject g3 = generateGameObject();
        ReflectionTestUtils.setField(g3, "velocity", new Vector2D(1, 1));
        ReflectionTestUtils.invokeMethod(g3, "move");
        Assert.assertEquals(new Vector2D(1, 1), g3.getPosition());
    }


    @Test
    public void testMultipleMovement() {
        int movementCount = 5;
        Vector2D velocity = new Vector2D(3, 5);
        GameObject gameObject = generateGameObject();
        ReflectionTestUtils.setField(gameObject, "velocity", velocity);
        for (int i = 0; i < movementCount; i++) {
            ReflectionTestUtils.invokeMethod(gameObject, "move");
        }
        Assert.assertEquals(velocity.getMultiplied(movementCount), gameObject.getPosition());
    }

    @Test
    public void testIsColliding() {
        GameObject g1 = generateGameObject();
        GameObject g2 = generateGameObject();

        // Move one object a little so that they are still colliding.
        g1.getRigidBody().reposition(Vector2D.up());
        Assert.assertTrue(g1.isColliding(g2));
        // Method should be commutative as well
        Assert.assertTrue(g2.isColliding(g1));

        // Move object further so that they are not colliding anymore.
        g1.getRigidBody().reposition(new Vector2D(500, 500));
        Assert.assertFalse(g1.isColliding(g2));
        // Method should be commutative as well
        Assert.assertFalse(g2.isColliding(g1));
    }

    private void setPlayersMovementSpeed(Spaceship playerSpaceship, double movementSpeed) {
        SpaceshipStats stats = (SpaceshipStats) ReflectionTestUtils.getField(playerSpaceship, "spaceshipStats");
        Assert.assertNotNull(stats);
        ReflectionTestUtils.setField(stats, "movementSpeed", movementSpeed);
    }

    @Test
    public void testPlayerSpaceshipOnMousePositionChange() {
        long dummyUserId = authTestUtil.generateRegisteredUser().getId();
        PlayerSpaceship playerSpaceship = GameObjectFactory.getInstance().getPlayerSpaceship(dummyUserId,
                SpaceshipTier.TIER1);

        Vector2D destination = Vector2D.zero();

        // When new position is equal to the last position.
        playerSpaceship.getRigidBody().reposition(destination);
        playerSpaceship.onMousePositionChange(destination);
        Assert.assertEquals(Vector2D.zero(), playerSpaceship.getVelocity());

        // When destination cannot be reached at a single frame.
        playerSpaceship.getRigidBody().reposition(Vector2D.zero());
        double movementSpeed = 5;
        setPlayersMovementSpeed(playerSpaceship, movementSpeed);
        destination = new Vector2D(40, 30);
        playerSpaceship.onMousePositionChange(destination);
        playerSpaceship.updateOnFrame();
        Vector2D expectedVelocity = new Vector2D(4, 3);
        // Check if velocity is set correctly
        Assert.assertEquals(expectedVelocity, playerSpaceship.getVelocity());

        // When destination is so near that it can be reached in a single frame.
        destination = new Vector2D(0.1, 0.1);
        Vector2D currentPosition = Vector2D.zero();
        playerSpaceship.getRigidBody().reposition(currentPosition);
        playerSpaceship.onMousePositionChange(destination);
        playerSpaceship.updateOnFrame();
        expectedVelocity = destination.getSubtracted(currentPosition);
        // Check if velocity is set correctly
        Assert.assertEquals(expectedVelocity, playerSpaceship.getVelocity());
    }
}
