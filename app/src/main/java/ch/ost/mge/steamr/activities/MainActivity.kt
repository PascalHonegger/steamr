package ch.ost.mge.steamr.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.text.isDigitsOnly
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import ch.ost.mge.steamr.R
import ch.ost.mge.steamr.adapter.SteamIdAdapter
import ch.ost.mge.steamr.databinding.ActivityMainBinding
import ch.ost.mge.steamr.util.applyTheme
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

private const val REQUEST_TAG = "GetSteamProfileTag"

private const val STEAM_ID_HISTORY = "SteamIdHistory"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var requestQueue: RequestQueue
    private lateinit var preferences: SharedPreferences
    private lateinit var steamIdAdapter: SteamIdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestQueue = Volley.newRequestQueue(this)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        applyTheme(preferences)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        steamIdAdapter = SteamIdAdapter { binding.editTextSteamId.setText(it) }

        readSteamIds()

        binding.oldSteamIdRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.oldSteamIdRecyclerView.adapter = steamIdAdapter

        binding.viewProfileButton.setOnClickListener {
            storeSteamId()
            fetchProfile()
            readSteamIds()
        }
    }

    override fun onStop() {
        super.onStop()
        requestQueue.cancelAll(REQUEST_TAG)
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
        val steamIds = preferences.getString(STEAM_ID_HISTORY, "")!!.split(";").filter { it.isNotBlank() }
        steamIdAdapter.submitList(steamIds)
    }

    private fun storeSteamId() {
        val existingProperties = preferences.getString(STEAM_ID_HISTORY, "")
        preferences.edit {
            putString(STEAM_ID_HISTORY, existingProperties + ";" + binding.editTextSteamId.text)
        }
    }

    private fun fetchProfile() {
        // Examples of valid URLs
        // https://steamcommunity.com/id/stupsi?xml=1
        // https://steamcommunity.com/profiles/76561198425286017?xml=1
        binding.viewProfileButton.isEnabled = false
        val url = binding.editTextSteamId.text.let {
            if (it.isDigitsOnly()) {
                "https://steamcommunity.com/profiles/$it?xml=1"
            } else {
                "https://steamcommunity.com/id/$it?xml=1"
            }
        }

        val request = StringRequest(Request.Method.GET, url,
            {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                binding.viewProfileButton.isEnabled = true
            },
            {
                Toast.makeText(this, "Request failed", Toast.LENGTH_SHORT).show()
                binding.viewProfileButton.isEnabled = true
            })
        request.tag = REQUEST_TAG
        requestQueue.add(request)
    }
}