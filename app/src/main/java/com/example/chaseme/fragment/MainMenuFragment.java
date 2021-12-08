package com.example.chaseme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chaseme.GameActivity;
import com.example.chaseme.R;
import com.example.chaseme.object.Leaderboard;
import com.example.chaseme.object.Score;
import com.example.chaseme.object.Settings;
import com.example.chaseme.databinding.MainMenuFragmentBinding;
import com.example.chaseme.object.SharedPref;

public class MainMenuFragment extends Fragment {

    private MainMenuFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MainMenuFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vérifie que l'username n'est pas vide avant de crée une partie. Si l'username est vide alors redirige vers le settings fragment
        binding.fab.setOnClickListener(view14 -> {
            if (Settings.getInstance().getUsername().isEmpty()) {
                Toast.makeText(binding.getRoot().getContext(), "Ajouter un username.", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(MainMenuFragment.this)
                        .navigate(R.id.action_MainMenuFragment_to_MainSettingsFragment);
            } else {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        // Redirige vers le Leaderboard fragment.
        binding.buttonLeaderboard.setOnClickListener(view1 -> NavHostFragment.findNavController(MainMenuFragment.this)
                .navigate(R.id.action_MainMenuFragment_to_MainLeaderboardFragment));

        // Redirige vers le Settings fragment.
        binding.buttonSettings.setOnClickListener(view12 -> NavHostFragment.findNavController(MainMenuFragment.this)
                .navigate(R.id.action_MainMenuFragment_to_MainSettingsFragment));

        // Quit l'application.
        binding.buttonQuit.setOnClickListener(view13 -> getActivity().finishAffinity());
    }

    /**
     * Prends place au retour du GameActivity. Crée un nouveau score et l'ajoute au leaderboard s'il y a des points dans le data.
     * @param requestCode Le code de l'Activité crée.
     * @param resultCode Code du résultat de l'activité
     * @param data Les données transmises par l'activité
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            int points = data.getIntExtra("POINTS", -1);
            if (points != -1) {
                Score score = new Score(points);
                Leaderboard.getInstance().setUserScore(Settings.getInstance().getUsername(), score);
                SharedPref.getInstance().saveLeaderboard();
                binding.lastgameTextView.setText(String.format("Last game : \n%s : %d", score.getUsername(), score.getScore()));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}