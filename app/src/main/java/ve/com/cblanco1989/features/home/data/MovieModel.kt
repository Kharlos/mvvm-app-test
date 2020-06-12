package ve.com.cblanco1989.features.home.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable {


    fun movieTitle():String{
        return "${title}"
    }
    fun formatUrl():String{
        return "https://image.tmdb.org/t/p/w500${poster_path}"
    }
}