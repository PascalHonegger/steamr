package ch.ost.mge.steamr.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.preference.PreferenceFragmentCompat
import ch.ost.mge.steamr.R
import ch.ost.mge.steamr.databinding.SettingsActivityBinding
import ch.ost.mge.steamr.util.applyTheme

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(binding.settings.id, SettingsFragment())
                setReorderingAllowed(true)
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onSharedPreferenceChanged(preferences: SharedPreferences, key: String) {
            if (key == "theme") {
                applyTheme(preferences)
            }
        }

        override fun onResume() {
            super.onResume()
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }
    }
}