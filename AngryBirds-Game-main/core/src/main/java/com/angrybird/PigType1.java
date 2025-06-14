package com.angrybird;

public class PigType1 {

    private Float density;
    private Float friction;

    public Integer health;


    public PigType1() {

        density = 1.0f;
        friction = 0.5f;

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
}
