package com.angrybird;

public class PigType2 {

    private Float density;
    private Float friction;

    public Integer health;


    public PigType2() {

        density = 1.0f;
        friction = 0.5f;

    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Float getDensity() {
        return density;
    }


    public Float getFriction() {
        return friction;
    }
}
