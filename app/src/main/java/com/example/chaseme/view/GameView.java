package com.example.chaseme.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.chaseme.object.entity.Player;
import com.example.chaseme.object.entity.Prey;
import com.example.chaseme.object.entity.Danger;

import java.util.ArrayList;

public class GameView extends View {
    private Player player;
    private Prey prey;
    private ArrayList<Danger> dangers;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setBackgroundColor(Color.WHITE);
        player = new Player(context);
        prey = new Prey(0, 0);
        dangers = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public Prey getPrey() {
        return prey;
    }

    public ArrayList<Danger> getDangers() {
        return dangers;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPrey(Prey prey) {
        this.prey = prey;
    }

    public void setDangers(ArrayList<Danger> dangers) {
        this.dangers = dangers;
    }

    /**
     * Mets à jour la view pour la redessiner.
     */
    public void updateEntities() {
        this.invalidate();
    }

    /**
     * Mets à jour les entités stockées dans la view et la view pour la redessiner.
     * @param player Une Entité Joueur
     * @param prey Une Entité Cible
     * @param dangers Des Entités de Danger
     */
    public void updateEntities(Player player, Prey prey, ArrayList<Danger> dangers) {
        this.setPlayer(player);
        this.setPrey(prey);
        this.setDangers(dangers);
        this.invalidate();
    }

    /**
     * Dessine les Entités à l'aide du Canvas.
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        player.draw(canvas);
        prey.draw(canvas);
        dangers.forEach(danger -> danger.draw(canvas));
    }
}
