package com.group14.termproject.server.game.model.bullet;

import com.group14.termproject.server.game.model.GameObjectDTO;
import com.group14.termproject.server.game.util.enums.BulletType;
import com.group14.termproject.server.game.util.enums.SpaceshipType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BulletDTO extends GameObjectDTO {
    private BulletType type;
    private SpaceshipType ownerType;
}
