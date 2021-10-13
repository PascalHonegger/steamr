package ch.ost.mge.steamr.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import ch.ost.mge.steamr.databinding.ActivityMainBinding
import ch.ost.mge.steamr.util.applyTheme

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyTheme(PreferenceManager.getDefaultSharedPreferences(this))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNavigate.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}