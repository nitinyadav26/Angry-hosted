package com.angrybird;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class PinkBird {
    private Float initialX;
    private Float initialY;
    private Float density;
    private Float friction;
    public Integer health = 0;

    public PinkBird() {
        initialX = 4.6f;
        initialY = 4.88f;
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
