package com.group14.termproject.server.api;

import com.group14.termproject.server.game.management.GameRoomService;
import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.model.GameStatusDTO;
import com.group14.termproject.server.service.JwtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("game")
public class GameController {
    private final GameRoomService gameRoomService;
    private final JwtService jwtService;


    public GameController(GameRoomService gameRoomService, JwtService jwtService) {
        this.gameRoomService = gameRoomService;
        this.jwtService = jwtService;
    }

    private ResponseEntity<Map<String, String>> getSuccessResponse() {
        return new ResponseEntity<>(Collections.singletonMap("message", "Success"), HttpStatus.OK);
    }

    private long getUserId(String authorizationHeader) {
        return jwtService.getUserId(jwtService.extractToken(authorizationHeader));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new game room",
            notes = "Must provide the authorization token that is given to user user when logging in",
            response = Map.class)
    public ResponseEntity<Map<String, String>> createRoom(@RequestHeader("Authorization") String authorizationHeader) {
        gameRoomService.createGameRoom(getUserId(authorizationHeader));
        return getSuccessResponse();
    }

    @ApiOperation(value = "Starts the game on previously created game room",
            notes = "Must provide the authorization token that is given to user user when logging in",
            response = Map.class)
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> startGame(@RequestHeader("Authorization") String authorizationHeader)
            throws IllegalAccessException {
        gameRoomService.startGame(getUserId(authorizationHeader));
        return getSuccessResponse();
    }

    @ApiOperation(value = "Returns a game status json including game objects, current level and the state of the game",
            notes = "Must provide the authorization token that is given to user user when logging in",
            response = GameStatusDTO.class)
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public GameStatusDTO getGameStatus(@RequestHeader("Authorization") String authorizationHeader)
            throws IllegalAccessException {
        return gameRoomService.getGameStatus(getUserId(authorizationHeader));
    }

    @ApiOperation(value = "Repositions the player depending on mouse input",
            notes = "Must provide the authorization token that is given to user user when logging in and a vector" +
                    " that contains x,y coordinates",
            response = Map.class)
    @RequestMapping(value = "/reposition", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> notifyNewMousePosition(@RequestHeader("Authorization") String authorizationHeader,
                                                                      @RequestBody Vector2D newPosition) throws IllegalAccessException {
        gameRoomService.notifyMousePositionChange(getUserId(authorizationHeader), newPosition);
        return getSuccessResponse();
    }

    @ApiOperation(value = "Applies a cheat that kills all the enemies in the current level and passes to the next level",
            notes = "Must provide the authorization token that is given to user user when logging in",
            response = Map.class)
    @RequestMapping(value = "/cheat", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> applyCheat(@RequestHeader("Authorization") String authorizationHeader)
            throws IllegalAccessException {
        gameRoomService.cheat(getUserId(authorizationHeader));
        return getSuccessResponse();
    }
}
