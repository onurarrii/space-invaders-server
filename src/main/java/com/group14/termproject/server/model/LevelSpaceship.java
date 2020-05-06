package com.group14.termproject.server.model;

import com.group14.termproject.server.game.util.Vector2D;
import com.group14.termproject.server.game.util.enums.SpaceshipTier;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelSpaceship {

    private SpaceshipTier tier;

    private Vector2D position;

    public LevelSpaceship(SpaceshipTier tier, Vector2D position) {
        this.tier = tier;
        this.position = new Vector2D(position);
    }
}
