package com.group14.termproject.server.game.management;

import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.model.GameStatusDTO;
import com.group14.termproject.server.model.LeaderboardEntry;
import com.group14.termproject.server.model.User;
import com.group14.termproject.server.repository.UserRepository;
import com.group14.termproject.server.service.LeaderboardService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;

@Service
public class GameRoomServiceImpl implements GameRoomService {

    private final LeaderboardService leaderboardService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final Map<Long, GameManager> gameRooms = new HashMap<>();
    private final Map<GameManager, Thread> gameThreads = new HashMap<>();

    public GameRoomServiceImpl(LeaderboardService leaderboardService, UserRepository userRepository, ModelMapper modelMapper) {
        this.leaderboardService = leaderboardService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createGameRoom(long userId) throws IllegalStateException, NoSuchElementException {
        tryToGetUser(userId); // Check if a user with <userId> exists
        GameManager usersGameRoom = gameRooms.get(userId);
        if (usersGameRoom != null) { // The user already has a game. Close it before opening a new one.
            usersGameRoom.onGameCompleted();
        }
        GameManager gameManager = new GameManager(userId);
        gameManager.bindOnGameCompletedCallback(getGameCompletedCallback());
        gameRooms.put(userId, gameManager);
        gameThreads.put(gameManager, new Thread(gameManager));
    }

    @Override
    public void startGame(long userId) throws IllegalAccessException {
        GameManager gameManager = tryToGetUsersGameRoom(userId);
        if (gameManager.isGameStarted())
            throw new IllegalAccessException("The game has already started");
        gameThreads.get(gameManager).start();
    }

    @Override
    public void notifyMousePositionChange(long userId, Vector2D position) throws IllegalAccessException {
        GameManager gameManager = tryToGetUsersGameRoom(userId);
        if (!gameManager.isGameStarted())
            throw new IllegalAccessException("Cannot notify a game which has not started yet.");
        gameManager.onMousePositionChange(position);
    }

    @Override
    public GameStatusDTO getGameStatus(long userId) throws IllegalAccessException {
        GameManager gameRoom = tryToGetUsersGameRoom(userId);
        GameStatusDTO gameStatus = modelMapper.map(gameRoom, GameStatusDTO.class);
        gameStatus.setScore(gameRoom.getPlayerSpaceship().getScore());
        return gameStatus;
    }

    @Override
    public void cheat(long userId) throws IllegalAccessException {
        tryToGetUsersGameRoom(userId).cheat();
    }

    private void submitScore(User user, double score) {
        leaderboardService.submitScore(new LeaderboardEntry(user, score));
    }

    private Consumer<GameManager> getGameCompletedCallback() {
        return gameManager -> {
            double score = gameManager.getPlayerSpaceship().getScore();
            User user = tryToGetUser(gameManager.getUserId());
            submitScore(user, score);
            this.gameRooms.remove(user.getId());
            this.gameThreads.remove(gameManager);
        };
    }

    private GameManager tryToGetUsersGameRoom(long userId) throws IllegalAccessException {
        GameManager gameRoom = gameRooms.get(userId);
        if (gameRoom == null) {
            throw new IllegalAccessException(String.format("No game room found for given user with id: %s", userId));
        }
        return gameRoom;
    }

    private User tryToGetUser(long userId) throws NoSuchElementException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new NoSuchElementException();
        return user.get();
    }
}
