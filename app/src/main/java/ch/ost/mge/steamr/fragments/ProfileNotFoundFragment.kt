package ch.ost.mge.steamr.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.ost.mge.steamr.R
import ch.ost.mge.steamr.databinding.FragmentProfileNotFoundBinding

const val PROFILE_ID = "profile_id"

class ProfileNotFoundFragment : Fragment() {
    private var profileId: String? = null

    private var _binding: FragmentProfileNotFoundBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profileId = it.getString(PROFILE_ID)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileNotFoundBinding.inflate(inflater, container, false)

        binding.profileNotFoundTextView.text = getString(R.string.profile_not_found, profileId)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param profileId The user entered ID.
         * @return A new instance of fragment ProfileNotFoundFragment.
         */
        @JvmStatic
        fun newProfileNotFoundFragment(profileId: String) =
            ProfileNotFoundFragment().apply {
                arguments = Bundle().apply {
                    putString(PROFILE_ID, profileId)
                }
            }
    }
}