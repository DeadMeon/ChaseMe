package com.example.chaseme.object.entity;

import android.content.Context;

import com.example.chaseme.object.Entity;
import com.example.chaseme.object.Settings;

public class Player extends Entity {
    public static final int RADIUS = 50;
    private int points;


    public Player(Context context) {
        super(context.getResources().getDisplayMetrics().widthPixels / 2, context.getResources().getDisplayMetrics().heightPixels / 2, RADIUS);
        setBitmap(drawableToBitmap(Settings.getInstance().getHunterDrawable()));
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Ajoute un points au score du player.
     */
    public void addPoints() {
        points++;
    }

    /**
     * Ajoute n points au score du player
     * @param n Le nombre de points a ajouté.
     */
    public void addPoints(int n) {
        this.points += n;
    }


}
