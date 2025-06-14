package com.angrybird;

public class GlassBlock {

    private Float density;
    private Float friction;
    public Integer health;


    public GlassBlock() {

        density = 1.0f;
        friction = 0.9f;
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
