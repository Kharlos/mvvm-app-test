package ve.com.cblanco1989.common.networking.interfaceapiservices

import retrofit2.Call
import retrofit2.http.GET
import ve.com.cblanco1989.common.networking.contract.MoviePopularResponse

interface MovieApiService {

    @GET("3/movie/popular")
    fun getMoviesPopular() : Call<MoviePopularResponse>

}