package com.group14.termproject.server.game.management;

import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.model.GameStatusDTO;

import java.util.NoSuchElementException;

public interface GameRoomService {
    /**
     * It is used when a user wants to play the game and creates a room for himself/herself.
     * If the user already has a room, it is replaced with the new one.
     *
     * @param userId id of the user who requested to create a game room.
     * @throws IllegalStateException  thrown when the user already has an ongoing game.
     * @throws NoSuchElementException thrown when there exists no user with {@code userId}.
     */
    void createGameRoom(long userId) throws IllegalStateException, NoSuchElementException;

    /**
     * Starts the game that belongs to the user with {@code userId}.
     *
     * @param userId id of the requester.
     * @throws IllegalAccessException thrown when user has no active game room. It is required to call
     *                                {@link GameRoomService#createGameRoom(long)} method before that.
     */
    void startGame(long userId) throws IllegalAccessException;

    /**
     * It is used for notifying the game of the user about his/her mouse position change so that player's spaceship
     * can be repositioned.
     *
     * @param userId   id of the requester.
     * @param position new position of the mouse.
     * @throws IllegalAccessException thrown when user has no active game room.
     */
    void notifyMousePositionChange(long userId, Vector2D position) throws IllegalAccessException;

    /**
     * It is used for retrieving information of all objects in the game and its status.
     * The result includes required fields for rendering a frame.
     *
     * @param userId id of the requester.
     * @return status info and game objects that currently resides in the game of the user with {@code userId}.
     * @throws IllegalAccessException thrown when user has no active game room.
     */
    GameStatusDTO getGameStatus(long userId) throws IllegalAccessException;

    /**
     * Instantly the room of the user passes to the next level.
     *
     * @param userId id of the requester.
     * @throws IllegalAccessException thrown when user has no active game room.
     */
    void cheat(long userId) throws IllegalAccessException;
}
