package com.angrybird;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class RedBird {
    private Float initialX;
    private Float initialY;
    private Float density;
    private Float friction;

    public Integer health = 0;

    public RedBird() {
        initialX = 1.5f;
        initialY = 2.3f;
        density = 1.0f;
        friction = 0.5f;
        health = 1;
    }

    public void setHealth(Integer healthin) {
        health = healthin;
    }

    public Float getInitialX() {
        return initialX;
    }

    public Float getInitialY() {
        return initialY;
    }

    public Float getDensity() {
        return density;
    }

    public Float getFriction() {
        return friction;
    }

    public int getHealth() {
        return health;
    }


}
