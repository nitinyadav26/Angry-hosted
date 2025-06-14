package com.angrybird;

import com.badlogic.gdx.math.Vector2;

public class PigType3 {

    private Float density;
    private Float friction;
    public Integer health;
    private Vector2 position; // to hold the pig's position.
    private boolean isJumping; // tracks if the pig is moving up or down.

    public PigType3(Vector2 initialPosition) {
        density = 1.0f;
        friction = 0.5f;
        health = 1;
        position = initialPosition;
        isJumping = true;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getHealth() {
        return health;
    }

    public Float getDensity() {
        return density;
    }

    public Float getFriction() {
        return friction;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void updateJump(float screenHeight, float groundHeight) {
        float maxHeight = screenHeight; // Maximum height (adjust as needed)
        float minHeight = groundHeight + 220f; // Minimum height (ground level + radius)

        if (isJumping) {
            position.y += 5;

            if (position.y >= maxHeight) {
                isJumping = false;
            }
        } else {
            position.y -= 5;

            if (position.y <= minHeight) {
                isJumping = true;
            }
        }
    }
}
