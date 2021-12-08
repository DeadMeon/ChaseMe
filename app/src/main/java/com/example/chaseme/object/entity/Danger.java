package com.example.chaseme.object.entity;

import com.example.chaseme.object.Entity;
import com.example.chaseme.object.Settings;

public class Danger extends Entity {
    public static final int RADIUS = 32;

    public Danger(int xPos, int yPos) {
        super(xPos, yPos, RADIUS);
        setBitmap(drawableToBitmap(Settings.getInstance().getDangerDrawable()));
        setHaveCollision(false);
    }
}
