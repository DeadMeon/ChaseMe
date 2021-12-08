package com.example.chaseme.object;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.chaseme.R;

public class Settings {


    private static final Settings settings = new Settings();
    private String username;
    private int[] gameType;
    private Context context;

    private Settings() {
        username = "";
        gameType = new int[]{0,0,0};
    }

    public static final Settings getInstance() {
        return settings;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int[] getGameType() {
        return gameType;
    }

    public void setHunterGameType(int gameType) {
        this.gameType[0] = gameType;
    }

    public void setPreyGameType(int gameType) {
        this.gameType[1] = gameType;
    }

    public void setDangerGameType(int gameType) {
        this.gameType[2] = gameType;
    }

    public void setGameType(int[] gameType) {
        this.gameType = gameType;
    }

    public void setGameType(int gameType1, int gameType2, int gameType3) {
        this.setHunterGameType(gameType1);
        this.setPreyGameType(gameType2);
        this.setDangerGameType(gameType3);
    }

    /**
     * Retourne le texte liée au game type du Hunter.
     * @param gameType
     * @return
     */
    public String getHunterName(int[] gameType) {
        switch (gameType[0]) {
            case 1:
                return "Thief";
            default:
                return "Cat";
        }
    }

    /**
     * Retourne le Drawable lié au game type du Hunter.
     * @return
     */
    public Drawable getHunterDrawable() {
        switch (gameType[0]) {
            case 1:
                return context.getDrawable(R.drawable.svg_thief);
            default:
                return context.getDrawable(R.drawable.svg_cat);
        }
    }

    /**
     * Retourne le Drawable lié au game type de la Prey.
     * @return
     */
    public Drawable getPreyDrawable() {
        switch (gameType[1]) {
            case 1:
                return context.getDrawable(R.drawable.svg_coins);
            default:
                return context.getDrawable(R.drawable.svg_mouse);
        }
    }

    /**
     * Retourne le Drawable lié au game type du Danger.
     * @return
     */
    public Drawable getDangerDrawable() {
        switch (gameType[2]) {
            case 1:
                return context.getDrawable(R.drawable.svg_police);
            default:
                return context.getDrawable(R.drawable.svg_blood);
        }
    }


    /**
     * Charge les données depuis les Shareds Preference.
     */
    public void load() {
        username = SharedPref.getInstance().loadSettingsUsername();
        gameType = SharedPref.getInstance().loadSettingsGameType();
    }
}
