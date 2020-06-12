package ve.com.cblanco1989.features.home.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    private lateinit var binding:FragmentFirstBinding
    private lateinit var movieAdapter:MovieListAdapter

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

    }

    private fun setupViewModel() {
        movieListViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

        movieListViewModel.loaderControler.observe(viewLifecycleOwner, Observer {

           binding.loader.visibility = if(it) View.VISIBLE else View.GONE
        })

        movieListViewModel.moviePopularResponse.observe(viewLifecycleOwner, Observer {
            if(it != null) movieListViewModel.handleMovieListResponse() else showAnError()
        })

        movieListViewModel.movieList.observe(viewLifecycleOwner, Observer {
            if(it != null) showMovieList(it) else showAnError()
        })

        movieListViewModel.getPopularMovie()
    }

    private fun showMovieList(movieList : ArrayList<MovieModel>){

        movieAdapter = MovieListAdapter(object : MovieListAdapterInterface{
            override fun OnMovieSelected(movie: MovieModel) {
                findNavController().navigate(MoviesListFragmentDirections.actionFirstFragmentToMovieDetailFragment(movie))
            }
        })

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = movieAdapter
        }

        movieAdapter.submitList(movieList)
    }

    private fun showAnError() {


    }
}