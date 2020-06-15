package ve.com.cblanco1989.common.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ve.com.cblanco1989.common.networking.NetworkClient
import ve.com.cblanco1989.common.networking.contract.MoviePopularResponse
import ve.com.cblanco1989.common.networking.interfaceapiservices.MovieApiService
import ve.com.cblanco1989.features.home.data.MovieModel

class MoviesRepository {

    private val networkClient = NetworkClient<MovieApiService>()

    suspend fun getPopularMovies(moviesPopularResponse: MutableLiveData<MoviePopularResponse>){

        networkClient.getRetrofitService(MovieApiService::class.java)
            .getMoviesPopular()
            .enqueue(object : Callback<MoviePopularResponse>{
                override fun onFailure(call: Call<MoviePopularResponse>, t: Throwable) {
                    Log.d("EmitLog", "onFailure $t")
                    moviesPopularResponse.value = null
                }

                override fun onResponse(
                    call: Call<MoviePopularResponse>,
                    response: Response<MoviePopularResponse>
                ) {
                    Log.d("EmitLog", "onResponse ${Gson().toJson(response.body())}")

                    if(response.isSuccessful) moviesPopularResponse.value = response.body() else moviesPopularResponse.value = null
                }
            })
    }

}