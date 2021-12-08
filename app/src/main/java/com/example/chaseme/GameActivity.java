package com.example.chaseme;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chaseme.object.Score;
import com.example.chaseme.object.entity.Danger;
import com.example.chaseme.object.Leaderboard;
import com.example.chaseme.object.entity.Player;
import com.example.chaseme.object.entity.Prey;
import com.example.chaseme.object.Settings;
import com.example.chaseme.object.SharedPref;
import com.example.chaseme.view.GameView;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity implements SensorEventListener {
    private static final int SPEED = 5;

    private GameView gameView;
    private TextView scoreText;
    private TextView highscoreText;
    private TextView usernameText;

    private DisplayMetrics metrics ;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);
        scoreText = findViewById(R.id.scoreText);
        highscoreText = findViewById(R.id.highScoreTextView);
        usernameText = findViewById(R.id.usernameTextView);

        // Initialise les Sensors
        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Si Aucun Accelerometre, Ferme la partie et renvoie un petit message, sinon les enregistres
        if (mAccelerometer == null) {
            Toast.makeText(getApplicationContext(),"Pas d'Accéléromètre trouvé",Toast.LENGTH_SHORT).show();
            endGame(-1);
        } else {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
            metrics = this.getResources().getDisplayMetrics();

            // Crée la GameView ainsi que toutes les Entités necessaire.
            gameView.updateEntities(
                    new Player(this),
                    new Prey(
                    (int) (Math.random() * (metrics.widthPixels - 2 * Prey.RADIUS)) + Prey.RADIUS,
                    (int) (Math.random() * (metrics.heightPixels - 2 * Prey.RADIUS)) + Prey.RADIUS
                    ),
                    new ArrayList<>()
            );

            // Remplis les textes de scores
            usernameText.setText(Settings.getInstance().getUsername());
            highscoreText.setText("Highscore : " + Leaderboard.getInstance().getHighScore().getScore());
            scoreText.setText(String.format("Score : %d", gameView.getPlayer().getPoints()));
        }
    }

    /**
     * Enregistre les Sensors au réveil de l'application.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * Retire des listener les Sensors à la mise en veille de l'application.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    /**
     * En cas d'arrêt, enregistre les données et quitte l'application.
     */
    @Override
    protected void onStop() {
        super.onStop();
        SharedPref.getInstance().saveLeaderboard();
        SharedPref.getInstance().saveSettings();
    }

    /**
     * À l'aide d'un sensor event, si du bon type de sensors, va déplacer le joueur sur la GameView
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            boolean needUpdate = false;
            int nextPosX = (int) - sensorEvent.values[0] * SPEED + gameView.getPlayer().getxPos();
            if (nextPosX > Player.RADIUS && nextPosX < metrics.widthPixels - Player.RADIUS) {
                gameView.getPlayer().setxPos(nextPosX);
                needUpdate = true;
            }
            int nextPosY = (int) sensorEvent.values[1] * SPEED + gameView.getPlayer().getyPos();
            if (nextPosY > Player.RADIUS && nextPosY < metrics.heightPixels - Player.RADIUS) {
                gameView.getPlayer().setyPos(nextPosY);
                needUpdate = true;
            }
            if (needUpdate)
                checkPlayer();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    /**
     * Vérifie si le Hunter est entré en contact avec une Prey ou un Danger.
     * Si le Hunter et une Prey son en contact alors il va transformer la Prey en Danger sans collision le temps que le hunter s'éloigner et en faire apparaître une autre Prey.
     * Si le Hunter et un Danger entrent en contact alors la partie se termine.
     */
    private void checkPlayer() {
        if (gameView.getPlayer().intersectWith(gameView.getPrey())) {
            gameView.getPlayer().addPoints();
            scoreText.setText(String.format("Score : %d", gameView.getPlayer().getPoints()));
            gameView.getDangers().add(new Danger(gameView.getPrey().getxPos(), gameView.getPrey().getyPos()));
            do {
                gameView.setPrey(new Prey(
                        (int) (Math.random() * (metrics.widthPixels - 2 * Prey.RADIUS)) + Prey.RADIUS,
                        (int) (Math.random() * (metrics.heightPixels - 2 * Prey.RADIUS)) + Prey.RADIUS
                ));
            } while (gameView.getDangers().stream().anyMatch(danger -> danger.intersectWith(gameView.getPrey())));

        }
        gameView.getDangers().forEach(danger -> {
            if (!danger.isHaveCollision() && !gameView.getPlayer().intersectWith(danger, gameView.getPlayer().getRadius()))
                danger.setHaveCollision(true);
            if (danger.isHaveCollision() && gameView.getPlayer().intersectWith(danger)) {
                endGame(gameView.getPlayer().getPoints());
            }
        });
        gameView.updateEntities();
    }

    /**
     * Récupère le nombre de points du Player et le renvoie pour le startForResultActivity.
     * @param points
     */
    private void endGame(int points) {
        Intent intent=new Intent();
        intent.putExtra("POINTS",points);
        setResult(2,intent);
        finish();
    }
}
