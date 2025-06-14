package com.angrybird.screens;

public class HelperFunctions {
    protected static float CalcVelocityX(int initialX, int finalX, boolean triggered) {
        return (triggered) ? (-(finalX - initialX) * 0.05f) : 0;
    }

    protected static float CalcVelocityY(int initialY, int finalY, boolean triggered) {
        return (triggered) ? ((finalY - initialY) * 0.05f) : 0;
    }

    protected static float CalcXcoord(int initialX, float time, int velocityx, boolean triggered) {
        return (triggered) ? (initialX + velocityx * time) : initialX;
    }

    protected static float CalcYcoord(int initialY, float time, int velocityy, boolean triggered) {
        float calculated = (float) (initialY + velocityy * time - 4.9 * time * time);
        return (triggered) ? (calculated) : initialY;
    }

}
