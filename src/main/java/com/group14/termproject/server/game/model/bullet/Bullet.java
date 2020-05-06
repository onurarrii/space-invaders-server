package com.group14.termproject.server.game.model.bullet;

import com.group14.termproject.server.game.model.GameObject;
import com.group14.termproject.server.game.model.spaceship.Spaceship;
import com.group14.termproject.server.game.util.enums.BulletType;
import com.group14.termproject.server.game.util.enums.SpaceshipType;

public interface Bullet extends GameObject {
    void onCollision(Spaceship spaceship);

    BulletType getType();

    SpaceshipType getOwnerType();
}
