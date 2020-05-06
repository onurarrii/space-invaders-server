package com.group14.termproject.server.game.model;

import com.group14.termproject.server.game.util.Vector2D;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameObjectDTO {
    private Vector2D position;
    private Vector2D rotation;
}
