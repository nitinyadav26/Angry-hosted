package com.angrybird;

public class BlackBird {
    private Float initialX;
    private Float initialY;
    private Float density;
    private Float friction;
    public Integer health;

    // Constructor
    public BlackBird() {
        initialX = 2.6f;
        initialY = 1f;
        density = 1.0f;
        friction = 0.5f;
        health = 1;    // Health of the bird
    }

    public void setHealth(Integer healthin) {
        health = healthin;

    }

    public int getHealth() {
        return health;
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
}
