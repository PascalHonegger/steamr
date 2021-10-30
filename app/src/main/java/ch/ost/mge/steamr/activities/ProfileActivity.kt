package ch.ost.mge.steamr.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.commit
import ch.ost.mge.steamr.databinding.ActivityProfileBinding
import ch.ost.mge.steamr.fragments.ProfileFragment
import ch.ost.mge.steamr.fragments.ProfileNotFoundFragment
import ch.ost.mge.steamr.util.parseProfile
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.serialization.ExperimentalSerializationApi
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi

private const val REQUEST_TAG = "GetSteamProfileTag"

private const val STEAM_PROFILE_ID = "SteamProfileId"

private val URL_REGEX = Regex("""^https?://steamcommunity\.com""", RegexOption.IGNORE_CASE)

@ExperimentalXmlUtilApi
@ExperimentalSerializationApi
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val profileId = intent.extras?.getString(STEAM_PROFILE_ID) ?: intent.dataString
        if (profileId == null) {
            Log.wtf("Profile", "Cannot display profile without profile ID, finishing activity")
            finish()
            return
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        requestQueue = Volley.newRequestQueue(this)

        fetchProfile(profileId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        requestQueue.cancelAll(REQUEST_TAG)
    }

    private fun fetchProfile(id: String) {
        // Examples of valid URLs
        // https://steamcommunity.com/id/stupsi?xml=1
        // https://steamcommunity.com/profiles/76561198425286017?xml=1
        val url =
            when {
                id.isDigitsOnly() -> "https://steamcommunity.com/profiles/$id?xml=1"
                id.contains(URL_REGEX) -> Uri.parse(id).buildUpon()
                    .appendQueryParameter("xml", "1")
                    .scheme("https")
                    .toString()
                else -> "https://steamcommunity.com/id/$id?xml=1"
            }

        val request = StringRequest(
            Request.Method.GET, url,
            {
                try {
                    val parsed = parseProfile(it)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        val fragment = ProfileFragment.newProfileFragment(parsed)
                        replace(binding.fragmentContainerView.id, fragment)
                    }
                } catch (e: Throwable) {
                    Log.e("Profile", "Error while parsing profile response", e)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        val fragment = ProfileNotFoundFragment.newProfileNotFoundFragment(id)
                        replace(binding.fragmentContainerView.id, fragment)
                    }
                }
            },
            {
                Toast.makeText(this, it.localizedMessage ?: it.message, Toast.LENGTH_SHORT).show()
            })
        request.tag = REQUEST_TAG
        requestQueue.add(request)
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context, steamId: String) =
            Intent(context, ProfileActivity::class.java).apply {
                putExtra(STEAM_PROFILE_ID, steamId)
            }
    }
}
