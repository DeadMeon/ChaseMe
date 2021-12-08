package com.example.chaseme.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chaseme.R;
import com.example.chaseme.object.Score;
import com.example.chaseme.databinding.MainLeaderboardFragmentBinding;
import com.example.chaseme.object.Leaderboard;
import com.example.chaseme.object.Settings;

import java.util.Comparator;

public class MainLeaderboardFragment extends Fragment {

    private MainLeaderboardFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MainLeaderboardFragmentBinding.inflate(inflater, container, false);
        Leaderboard.getInstance().getLeaderboard().stream()
                .sorted(Comparator.comparingInt(Score::getScore).reversed())
                .forEach(this::accept);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Redirige vers le Menu fragment.
        binding.buttonPrevious.setOnClickListener(view1 -> NavHostFragment.findNavController(MainLeaderboardFragment.this)
                .navigate(R.id.action_MainLeaderboardFragment_to_MainMenuFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Fonction qui ajoute un TextView à partir dans un score dans le LinearLayout du leaderboard.
     * @param score Score d'un joueur
     */
    private void accept(Score score) {
        TextView textView = new TextView(getContext());
        textView.setText(String.format("%s : %d\n%s, %s\n",
                score.getUsername(),
                score.getScore(),
                score.getDate().toGMTString(),
                Settings.getInstance().getHunterName(score.getGameType()))
        );
        binding.LeaderboardLayout.addView(textView);
    }
}