package ch.ost.mge.steamr.fragments

import android.graphics.Bitmap
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
import ch.ost.mge.steamr.util.VolleySingleton
import com.android.volley.toolbox.NetworkImageView
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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

        requireActivity().title = profile.username ?: ""

        setImageURL(profile.avatarUrl, binding.avatarImageView)
        setImageURL(profile.inGameInfo?.bannerUrl, binding.gameIconImageView)

        binding.onlineStateTextView.text = HtmlCompat.fromHtml(profile.onlineState ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
        binding.vacBannedTextView.text = getString(
            if (profile.isVacBanned) R.string.vac_banned
            else R.string.not_vac_banned
        )

        profile.creationDate?.let {
            binding.creationDateTextView.text = getString(R.string.member_since, it.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)))
        }

        binding.summaryTextView.text =
            HtmlCompat.fromHtml(profile.summary ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setImageURL(url: String?, view: NetworkImageView) {
        if (url?.matches(Patterns.WEB_URL.toRegex()) == true) {
            view.setImageUrl(url, VolleySingleton.getInstance(requireContext()).imageLoader)
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
