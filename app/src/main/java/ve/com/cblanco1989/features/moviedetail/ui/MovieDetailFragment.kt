package ve.com.cblanco1989.features.moviedetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ve.com.cblanco1989.R
import ve.com.cblanco1989.databinding.MovieDetailFragmentBinding

class MovieDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    private lateinit var viewModel: MovieDetailViewModel

    private val args:MovieDetailFragmentArgs by navArgs()

    private lateinit var binding:MovieDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieDetailFragmentBinding.inflate(layoutInflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                movie = args.argMovie
            }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        requireActivity().title = args.argMovie.original_title


    }

}