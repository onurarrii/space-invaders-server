package com.group14.termproject.server.game.model.spaceship;

import com.group14.termproject.server.game.util.enums.SpaceshipTier;
import com.group14.termproject.server.game.util.enums.SpaceshipType;
import com.group14.termproject.server.game.model.GameObjectDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceshipDTO extends GameObjectDTO {
    private double maxHealth;
    private double health;
    private SpaceshipTier tier;
    private SpaceshipType type;
}
