package ve.com.cblanco1989.features.home.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ve.com.cblanco1989.common.networking.contract.MoviePopularResponse
import ve.com.cblanco1989.common.repository.MoviesRepository
import ve.com.cblanco1989.features.home.data.MovieModel

class MovieListViewModel : ViewModel() {

    private val moviesRepository = MoviesRepository()

    private val _moviePopularResponse = MutableLiveData<MoviePopularResponse>()
    var moviePopularResponse: LiveData<MoviePopularResponse> = _moviePopularResponse

    private val _loaderControler = MutableLiveData<Boolean>()
    var loaderControler: MutableLiveData<Boolean> = _loaderControler

    private val _movieList = MutableLiveData<ArrayList<MovieModel>>()
    var movieList: MutableLiveData<ArrayList<MovieModel>> = _movieList

    private val _filters = MutableLiveData<ArrayList<MovieModel>>()
    var filters: MutableLiveData<ArrayList<MovieModel>> = _filters

    fun getPopularMovie() {
        viewModelScope.launch {
            _loaderControler.value = true
            moviesRepository.getPopularMovies(_moviePopularResponse)
        }
    }

    fun handleMovieListResponse() {
        _loaderControler.value = false
        if (_moviePopularResponse.value != null) {

            var mList = _moviePopularResponse.value?.toMovieListModel()
            mList?.sortBy { it.title }
            _movieList.value = mList
        } else {
            _movieList.value = null
        }
    }

    fun filterByName(query: String) {
        _movieList.value?.let { list ->
            if (query.isNullOrBlank()) {
                _filters.value =  list
            } else {
                _filters.value = list.filter { it.title.contains(query, true) } as ArrayList
            }
        }
    }

}