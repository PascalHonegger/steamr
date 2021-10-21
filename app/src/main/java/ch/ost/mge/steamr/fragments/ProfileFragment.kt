package ch.ost.mge.steamr.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.ost.mge.steamr.databinding.FragmentProfileBinding
import ch.ost.mge.steamr.util.Profile

private const val ARG_PROFILE = "profile";

class ProfileFragment : Fragment() {
    private var profile: Profile? = null
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profile = it.getParcelable(ARG_PROFILE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        profile.let {
            if (it == null) {
                Log.wtf("ProfileFragment", "Cannot display a profile fragment without a profile")
            } else {
                activity?.title = it.username
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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