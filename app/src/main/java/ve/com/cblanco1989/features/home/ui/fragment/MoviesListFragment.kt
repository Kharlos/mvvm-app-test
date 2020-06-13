package ve.com.cblanco1989.features.home.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ve.com.cblanco1989.R
import ve.com.cblanco1989.databinding.FragmentFirstBinding
import ve.com.cblanco1989.features.home.data.MovieModel
import ve.com.cblanco1989.features.home.ui.adapter.MovieListAdapter
import ve.com.cblanco1989.features.home.ui.adapter.MovieListAdapterInterface

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MoviesListFragment : Fragment() {

    private lateinit var movieListViewModel: MovieListViewModel

    private lateinit var binding: FragmentFirstBinding
    private lateinit var movieAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        setupUI()
    }

    private fun setupUI() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0?.toString().isNullOrBlank())
                    movieListViewModel.filterByName(p0.toString())
            }
        })
    }

    private fun setupViewModel() {
        movieListViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

        movieListViewModel.loaderControler.observe(viewLifecycleOwner, Observer {

            binding.loader.visibility = if (it) View.VISIBLE else View.GONE
        })

        movieListViewModel.moviePopularResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) movieListViewModel.handleMovieListResponse() else showAnError()
        })

        movieListViewModel.movieList.observe(viewLifecycleOwner, Observer {
            if (it != null) showMovieList(it) else showAnError()
        })

        movieListViewModel.filters.observe(viewLifecycleOwner, Observer {
            if (it != null) showMovieList(it) else showAnError()
        })

        movieListViewModel.getPopularMovie()
    }

    private fun showMovieList(movieList: ArrayList<MovieModel>) {

        movieAdapter = MovieListAdapter(object : MovieListAdapterInterface {
            override fun OnMovieSelected(movie: MovieModel) {
                findNavController().navigate(
                    MoviesListFragmentDirections.actionFirstFragmentToMovieDetailFragment(
                        movie
                    )
                )
            }
        })

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = movieAdapter
        }
        when(movieList.size){
            0->{
                emptyState()
            }
            else->{
                binding.rvMovies.visibility = View.VISIBLE
                movieAdapter.submitList(movieList)
            }
        }
    }

    private fun showAnError() {
        binding.loader.visibility = View.GONE
        binding.tvError.text = getString(R.string.empty_state)
        binding.tvError.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.GONE
    }

    private fun emptyState() {
        binding.tvError.text = getString(R.string.generic_error)
        binding.tvError.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.GONE

    }
}