package ch.ost.mge.steamr.fragments

import androidx.fragment.app.Fragment
import ch.ost.mge.steamr.R


class LoadingFragment : Fragment(R.layout.fragment_loading) {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_loading, container, false)
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment LoadingFragment.
         */
        @JvmStatic
        fun newLoadingFragment() = LoadingFragment()
    }
}