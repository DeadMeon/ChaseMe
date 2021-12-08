package com.example.chaseme.object.entity;

import com.example.chaseme.object.Entity;
import com.example.chaseme.object.Settings;

public class Prey extends Entity {
    public static final int RADIUS = 32;

    public Prey(int xPos, int yPos) {
        super(xPos, yPos, RADIUS);
        setBitmap(drawableToBitmap(Settings.getInstance().getPreyDrawable()));
    }
}