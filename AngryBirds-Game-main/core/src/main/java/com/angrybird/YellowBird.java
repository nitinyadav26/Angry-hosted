package com.angrybird;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class YellowBird {
    private Float initialX;
    private Float initialY;
    private Float density;
    private Float friction;
    public Integer health;

    public YellowBird() {
        initialX = 3.08f;
        initialY = 3f;
        density = 1.0f;
        friction = 0.5f;
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
