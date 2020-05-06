package com.group14.termproject.server.game.management;

import com.group14.termproject.server.AuthTestUtil;
import com.group14.termproject.server.game.util.enums.Level;
import com.group14.termproject.server.model.User;
import com.group14.termproject.server.service.JwtService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GameRoomServiceTests {
    @Autowired
    private AuthTestUtil authTestUtil;

    @Autowired
    JwtService jwtService;

    @Autowired
    GameRoomService gameRoomService;

    private User user;

    @Before
    public void init() {
        this.user = authTestUtil.generateRegisteredUser();
    }

    @Test
    public void testCreateGameRoom() {

        // When the user is doesn't exist then it should throw NoSuchElementException
        Assert.assertThrows(NoSuchElementException.class, () -> gameRoomService.createGameRoom(-1));

        // When a new game room is created then game room should not be null
        gameRoomService.createGameRoom(user.getId());
        GameManager gameRoom = ReflectionTestUtils.invokeMethod(gameRoomService, "tryToGetUsersGameRoom", user.getId());
        Assert.assertNotNull(gameRoom);
        Assert.assertEquals(ReflectionTestUtils.getField(gameRoom, "userId"), user.getId());

        // When the game room is already created then it should do nothing
        gameRoomService.createGameRoom(user.getId());
    }

    @Test
    public void testStartGame() {
        // When the game room has not created yet then it should throw an exception
        Assert.assertThrows(IllegalAccessException.class, () -> gameRoomService.startGame(user.getId()));
    }


    @Test
    public void testNotifyMousePositionChange() {
        // When the game room has not created yet then it should throw an exception
        Assert.assertThrows(IllegalAccessException.class, () -> gameRoomService.startGame(user.getId()));
    }

    @Test
    public void testCheat() throws IllegalAccessException, InterruptedException {
        gameRoomService.createGameRoom(user.getId());
        gameRoomService.startGame(user.getId());
        Thread.sleep(1000); // Wait until game is started
        GameManager gameRoom = ReflectionTestUtils.invokeMethod(gameRoomService, "tryToGetUsersGameRoom", user.getId());
        Assert.assertNotNull(gameRoom);
        Assert.assertEquals(Level.LEVEL_1, gameRoom.getLevel());
        gameRoomService.cheat(user.getId());
        Assert.assertEquals(Level.LEVEL_2, gameRoom.getLevel());
    }

}
