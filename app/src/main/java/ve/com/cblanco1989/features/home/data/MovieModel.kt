package ve.com.cblanco1989.features.home.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    var id: Int,
    var adult: Boolean,
    var backdrop_path: String,
    var genre_ids: List<Int>,
    var original_language: String,
    var original_title: String,
    var overview: String,
    var popularity: Double,
    var poster_path: String,
    var release_date: String,
    var title: String,
    var video: Boolean,
    var vote_average: Double,
    var vote_count: Int
) : Parcelable {

    fun voteAverage() = vote_average.toFloat()

    fun movieTitle():String{
        return "${title}"
    }

    fun formatUrl():String{
        return "https://image.tmdb.org/t/p/w500${poster_path}"
    }

    fun formatLang() = original_language
}