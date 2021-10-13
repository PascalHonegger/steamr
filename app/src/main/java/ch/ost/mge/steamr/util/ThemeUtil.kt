package ch.ost.mge.steamr.util

import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate

fun applyTheme(preferences: SharedPreferences) {
    val defaultNightMode = when (val theme = preferences.getString("theme", "system")) {
        "system" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        "dark" -> AppCompatDelegate.MODE_NIGHT_YES
        "light" -> AppCompatDelegate.MODE_NIGHT_NO
        else -> {
            Log.e("Theme", "Unknown theme config value $theme, defaulting to system")
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }

    AppCompatDelegate.setDefaultNightMode(defaultNightMode)
}