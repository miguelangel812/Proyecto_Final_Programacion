package com.example.proyecto_final_programacion.controller;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.example.proyecto_final_programacion.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    /*Cargar el layout de ajustes*/
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
