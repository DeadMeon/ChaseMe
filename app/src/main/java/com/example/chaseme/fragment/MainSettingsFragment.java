package com.example.chaseme.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chaseme.R;
import com.example.chaseme.object.Settings;
import com.example.chaseme.databinding.MainSettingsFragmentBinding;

public class MainSettingsFragment extends Fragment {
    private static final int MIN_USERNAME_LENGTH = 3;

    private MainSettingsFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MainSettingsFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.usernameTextView.setText(Settings.getInstance().getUsername());

        setupSpinner(binding.spinnerHunter, R.array.hunters);
        setupSpinner(binding.spinnerPrey, R.array.preys);
        setupSpinner(binding.spinnerDanger, R.array.dangers);

        // Récupère l'ID de l'items selectionné.
        binding.spinnerHunter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Settings.getInstance().setHunterGameType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerPrey.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Settings.getInstance().setPreyGameType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerDanger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Settings.getInstance().setDangerGameType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Vérifie que l'username n'est pas trop court sinon renvoie vers le main fragment.
        binding.buttonSave.setOnClickListener(view1 -> {
            if (binding.usernameTextView.getText().toString().length() < MIN_USERNAME_LENGTH) {
                Toast.makeText(binding.getRoot().getContext(), "Username pas assez long.", Toast.LENGTH_SHORT).show();
            } else {
                Settings.getInstance().setUsername(binding.usernameTextView.getText().toString());
                NavHostFragment.findNavController(MainSettingsFragment.this)
                        .navigate(R.id.action_MainSettingsFragment_to_MainMenuFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Initialise un Spinner avec une ressource donné.
     * @param spinner Le spinner a initialisé
     * @param resource La ressource sert a l'initialisation du Spinner.
     */
    public void setupSpinner(Spinner spinner, int resource) {
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this.getContext(), resource, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}