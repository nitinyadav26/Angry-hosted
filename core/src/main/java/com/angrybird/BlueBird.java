package com.angrybird;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class BlueBird {
    private Float initialX;
    private Float initialY;
    private Float density;
    private Float friction;
    private Float radius;
    public int health;
    private Body body;


    // Constructor
    public BlueBird() {
        initialX = 2.6f;
        initialY = 1f;
        density = 1.0f;
        friction = 0.5f;
        radius = 0.2f;
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

    public Float getRadius() {
        return radius;
    }

    public int getHealth() {
        return health;
    }

    public Body getBody() {
        return body;
    }
}
