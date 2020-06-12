package ve.com.cblanco1989.common.networking.contract

import ve.com.cblanco1989.features.home.data.MovieModel

class MoviePopularResponse {

    var page: Int = 1
    var total_results: Int = 1
    var total_pages: Int = 25
    var results: ArrayList<MovieModel>? = null

    fun toMovieListModel() = results
}