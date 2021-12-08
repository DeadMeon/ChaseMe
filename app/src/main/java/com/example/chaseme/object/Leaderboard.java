package com.example.chaseme.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Leaderboard {
    private static final Leaderboard singleton = new Leaderboard();
    private ArrayList<Score> leaderboard;

    public static Leaderboard getInstance() {
        return singleton;
    }

    // Getter and Setter

    public ArrayList<Score> getLeaderboard() {
        if (leaderboard == null)
            return new ArrayList<>();
        return leaderboard;
    }

    private void setLeaderboard(ArrayList<Score> scores) {
        leaderboard = scores;
    }

    /**
     * Récupère le Score de l'utilisateur en paramètre, si présent dans le leaderboard.
     * @param username Nom de l'utilisateur
     * @return Retourne soit le score provenant du leaderboard si présent, soit un nouveau score
     */
    public Score getUserScore(String username) {
        if (leaderboard.stream().anyMatch(x -> x.getUsername().equals(username))) {
            return leaderboard.stream().filter(x -> x.getUsername().equals(username)).findFirst().get();
        }
        return new Score();
    }

    /**
     * Essaye d'ajouter le score dans le leaderboard si l'utilisateur a fait un meilleur score ou s'il a battu l'un des 10 membres du leaderboard.
     * @param username Nom de l'utilisateur
     * @param score Le score de l'utilisateur
     */
    public void setUserScore(String username, Score score) {
        sortLeaderboard();
        if (leaderboard.stream().anyMatch(x -> x.getUsername().equals(username))) {
            if (leaderboard.stream().anyMatch(x -> x.getScore() < score.getScore()))
                leaderboard.stream().filter(x -> x.getUsername().equals(username)).findFirst().get().setScore(score);
        } else {
            addScoreToLeaderboard(score);
        }
        sortLeaderboard();
    }

    /**
     * Retourne le plus haut score du leaderbord ou 0 si aucun present.
     * @return Retourne un Score.
     */
    public Score getHighScore() {
        if (leaderboard.isEmpty())
            return new Score();
        sortLeaderboard();
        return leaderboard.get(0);
    }

    // Methode

    /**
     * Ajoute au leaderboard et si le tableau dépasse 10 retire le plus bas score.
     * @param score
     */
    private void addScoreToLeaderboard(Score score) {
        if (leaderboard.size() <= 10 || leaderboard.stream().anyMatch(x -> x.getScore() < score.getScore())) {
            if (leaderboard.size() == 10) {
                leaderboard.remove(10);
            }
            leaderboard.add(score);
        }
    }

    /**
     *  Trie le Leaderboard en fonction du plus haut score.
     */
    public void sortLeaderboard() {
        Collections.sort(leaderboard, new SortbyScore().reversed());
    }

    /**
     * Charge les données depuis les Shareds Preference.
     */
    public void load() {
        this.setLeaderboard(SharedPref.getInstance().loadLeaderboard());
    }
}

/**
 * Comparator pour comparer les Scores
 */
class SortbyScore implements Comparator<Score>
{
    @Override
    public int compare(Score score, Score t1) {
        return score.getScore() - t1.getScore();
    }
}