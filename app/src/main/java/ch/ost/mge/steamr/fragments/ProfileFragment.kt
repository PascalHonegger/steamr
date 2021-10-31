package ch.ost.mge.steamr.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import ch.ost.mge.steamr.R
import ch.ost.mge.steamr.databinding.FragmentProfileBinding
import ch.ost.mge.steamr.util.Profile
import java.net.URL
import java.util.concurrent.CompletableFuture

private const val ARG_PROFILE = "profile";

class ProfileFragment : Fragment() {
    private lateinit var profile: Profile
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var avatarBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        avatarBitmap = savedInstanceState?.getParcelable("avatarBitmap")
        val profile = arguments?.getParcelable<Profile>(ARG_PROFILE)
        if (profile == null) {
            Log.wtf("ProfileFragment", "Cannot display a profile fragment without a profile")
            activity?.supportFragmentManager?.popBackStack()
            return
        }
        this.profile = profile
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        setAvatar()
        requireActivity().title = profile.username ?: ""
        binding.onlineStateTextView.text = profile.onlineState ?: ""
        binding.vacBannedTextView.text = getString(
            if (profile.isVacBanned) R.string.vac_banned
            else R.string.not_vac_banned
        )
        binding.summaryTextView.text =
            HtmlCompat.fromHtml(profile.summary ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("avatarBitmap", avatarBitmap)
    }

    private fun setAvatar() {
        if (avatarBitmap != null) {
            binding.avatarImageView.setImageBitmap(avatarBitmap)
            return
        }
        val avatarUrl = profile.avatarUrl
        if (avatarUrl?.matches(Patterns.WEB_URL.toRegex()) != true) {
            return
        }
        CompletableFuture.runAsync {
            val url = URL(avatarUrl)
            url.openStream().use {
                val bitmap = BitmapFactory.decodeStream(it)
                requireActivity().runOnUiThread {
                    binding.avatarImageView.setImageBitmap(bitmap)
                    avatarBitmap = bitmap
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newProfileFragment(profile: Profile) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PROFILE, profile)
                }
            }
    }
}
