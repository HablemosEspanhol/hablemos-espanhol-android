package io.github.cadnunsdimir.android.javierchopeklecciones.ui

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "user_prefs")