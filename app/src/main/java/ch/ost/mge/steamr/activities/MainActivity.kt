package ch.ost.mge.steamr.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import ch.ost.mge.steamr.R
import ch.ost.mge.steamr.adapter.SteamIdAdapter
import ch.ost.mge.steamr.databinding.ActivityMainBinding
import ch.ost.mge.steamr.util.applyTheme
import kotlinx.serialization.ExperimentalSerializationApi
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi

private const val STEAM_ID_HISTORY = "SteamIdHistory"

@ExperimentalXmlUtilApi
@ExperimentalSerializationApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var steamIdAdapter: SteamIdAdapter
    private val steamIds = linkedSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        applyTheme(preferences)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        steamIdAdapter = SteamIdAdapter(
            onClick = { binding.editTextSteamId.setText(it) },
            onDelete = { removeSteamId(it) },
        )

        readSteamIds()

        binding.oldSteamIdRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.oldSteamIdRecyclerView.adapter = steamIdAdapter

        binding.viewProfileButton.isEnabled = false
        binding.editTextSteamId.addTextChangedListener { binding.viewProfileButton.isEnabled = !it.isNullOrBlank() }
        binding.editTextSteamId.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                if (binding.viewProfileButton.isEnabled) { binding.viewProfileButton.performClick() }
                true
            } else {
                false
            }
        }

        binding.viewProfileButton.setOnClickListener {
            binding.editTextSteamId.let {
                it.setText(it.text.trim())
                storeSteamId()
                startActivity(ProfileActivity.createIntent(this, it.text.toString()))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.settings_menu -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun readSteamIds() {
        steamIds.addAll(preferences.getString(STEAM_ID_HISTORY, "")!!.split(";").filter { it.isNotBlank() })
        steamIdAdapter.submitList(steamIds.toList())
    }

    private fun storeSteamId() {
        steamIds.add(binding.editTextSteamId.text.toString())
        preferences.edit {
            putString(STEAM_ID_HISTORY, steamIds.joinToString(";"))
        }
        steamIdAdapter.submitList(steamIds.toList())
    }

    private fun removeSteamId(id: String) {
        steamIds.remove(id)
        preferences.edit {
            putString(STEAM_ID_HISTORY, steamIds.joinToString(";"))
        }
        steamIdAdapter.submitList(steamIds.toList())
    }
}