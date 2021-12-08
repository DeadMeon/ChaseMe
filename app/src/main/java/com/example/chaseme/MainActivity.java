package com.example.chaseme;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.chaseme.databinding.ActivityMainBinding;
import com.example.chaseme.object.Leaderboard;
import com.example.chaseme.object.Settings;
import com.example.chaseme.object.SharedPref;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialise les Instances de Leaderboard et Settings
        SharedPref.getInstance().setContext(this);
        Settings.getInstance().setContext(this);
        Leaderboard.getInstance().load();
        Settings.getInstance().load();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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
}