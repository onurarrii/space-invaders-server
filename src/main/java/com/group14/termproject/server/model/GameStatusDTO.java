package com.group14.termproject.server.model;

import com.group14.termproject.server.game.model.bullet.BulletDTO;
import com.group14.termproject.server.game.model.spaceship.SpaceshipDTO;
import com.group14.termproject.server.game.util.enums.Level;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameStatusDTO {
    private Level level;
    private boolean gameCompleted;
    private boolean gameStarted;

    private double score;

    private List<SpaceshipDTO> spaceships = new ArrayList<>();
    private List<BulletDTO> bullets = new ArrayList<>();
}
