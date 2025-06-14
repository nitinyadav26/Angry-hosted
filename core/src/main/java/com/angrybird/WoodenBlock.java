package com.angrybird;

public class WoodenBlock {

    private Float density;
    private Float friction;
    public Integer health = 0;

    public WoodenBlock() {

        density = 1.0f;
        friction = 0f;
    }

    public Float getDensity() {
        return density;
    }

    public Float getFriction() {
        return friction;
    }
}
