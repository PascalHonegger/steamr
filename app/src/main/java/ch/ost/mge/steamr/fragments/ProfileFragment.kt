package ch.ost.mge.steamr.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import ch.ost.mge.steamr.databinding.FragmentProfileBinding
import ch.ost.mge.steamr.util.Profile
import java.net.URL
import java.util.concurrent.CompletableFuture

private const val ARG_PROFILE = "profile";

class ProfileFragment : Fragment() {
    private lateinit var profile: Profile
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        setProfileImage()
        requireActivity().title = profile.username ?: ""
        binding.onlineStateTextView.text = profile.onlineState ?: ""
        binding.vacBannedTextView.text = "Is ${ if(profile.isVacBanned) "" else "not"} VAC banned"
        binding.summaryTextView.text = HtmlCompat.fromHtml(profile.summary ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setProfileImage() {
        if (profile.avatarUrl == null) {
            return
        }
        CompletableFuture.runAsync {
            val url = URL(profile.avatarUrl)
            url.openStream().use {
                val bitmap = BitmapFactory.decodeStream(it)
                requireActivity().runOnUiThread {
                    binding.profileImageView.setImageBitmap(bitmap)
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