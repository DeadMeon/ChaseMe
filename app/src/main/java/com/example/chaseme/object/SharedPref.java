package com.example.chaseme.object;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Date;

public class SharedPref {
    private static final String FILE = "ChaseMeData";
    private static final SharedPref singleton  = new SharedPref();
    private SharedPreferences sharedPreferences;

    public static SharedPref getInstance() {
        return singleton;
    }

    public void setContext(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE, MODE_PRIVATE);
    }

    /**
     * Sauvegarde les données du Leaderboard dans les Shareds Preference.
     */
    public void saveLeaderboard() {
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Leaderboard.getInstance().getLeaderboard()
                .forEach(x -> myEdit.putString("Leaderboard" + Leaderboard.getInstance().getLeaderboard().indexOf(x), x.getScore() + ";" + x.getUsername() + ";" + x.getDate().getTime() + ";" + x.getGameType()[0] ));
        myEdit.apply();
    }

    /**
     * Retourne liste des scores sauvegarde dans les Shareds Preference pour le Leaderboard.
     * @return Une ArrayList des Scores .
     */
    public ArrayList<Score> loadLeaderboard() {
        ArrayList<Score> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String txt = sharedPreferences.getString("Leaderboard" + i, null);
            if (txt == null)
                break;
            String[] score = txt.split(";");
            list.add(new Score(Integer.parseInt(score[0]), new Date(Long.parseLong(score[2])), score[1], Integer.parseInt(score[3])));
        }
        return list;
    }

    /**
     * Sauvegarde les données du Settings dans les Shareds Preference.
     */
    public void saveSettings() {
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("SettingsUsername", Settings.getInstance().getUsername());
        myEdit.putInt("SettingsGameTypeHunter", Settings.getInstance().getGameType()[0]);
        myEdit.putInt("SettingsGameTypePrey", Settings.getInstance().getGameType()[1]);
        myEdit.putInt("SettingsGameTypeDanger", Settings.getInstance().getGameType()[2]);
        myEdit.apply();
    }

    /**
     * Retourne le nom d'utilisateur sauvegarde dans les Shareds Preference pour les Settings.
     * @return un string
     */
    public String loadSettingsUsername() {
        return sharedPreferences.getString("SettingsUsername", "");
    }

    /**
     * Retourne le gametype sauvegarde dans les Shareds Preference pour les Settings.
     * @return un tableau d'entier
     */
    public int[] loadSettingsGameType() {
        return new int[]{
                sharedPreferences.getInt("SettingsGameTypeHunter", 0 ),
                sharedPreferences.getInt("SettingsGameTypePrey", 0),
                sharedPreferences.getInt("SettingsGameTypeDanger", 0)
        };
    }
}
