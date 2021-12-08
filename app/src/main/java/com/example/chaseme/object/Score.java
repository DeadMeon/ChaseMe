package com.example.chaseme.object;

import java.util.Date;

public class Score {
    private int score;
    private String username;
    private Date date;
    private int[] gameType;

    public Score() {
        this.score = 0;
        this.username = Settings.getInstance().getUsername();
        this.gameType = Settings.getInstance().getGameType();
        this.date = new Date();
    }

    public Score(int score) {
        this.score = score;
        this.username = Settings.getInstance().getUsername();
        this.gameType = Settings.getInstance().getGameType();
        this.date = new Date();
    }

    public Score(int score, Date date) {
        this.score = score;
        this.username = Settings.getInstance().getUsername();
        this.gameType = Settings.getInstance().getGameType();
        this.date = date;
    }

    public Score(int score, Date date, String username, int hunterGameType) {
        this.score = score;
        this.username = username;
        this.gameType = new int[]{hunterGameType, 0, 0};;
        this.date = date;
    }


    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public Date getDate() {
        return date;
    }

    public int[] getGameType() {
        return gameType;
    }

    public void setHunterGameType(int gameType) {
        this.gameType[0] = gameType;
    }

    public void setScore(Score score) {
        this.score = score.score;
        this.username = score.username;
        this.gameType = score.gameType;
        this.date = score.date;
    }
}
