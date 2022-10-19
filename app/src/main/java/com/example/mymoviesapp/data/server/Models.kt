package com.example.mymoviesapp.data.server

import com.google.gson.annotations.SerializedName

data class ResponseModel(val results: MutableList<ApiMovie>) {

    val size: Int get() = results.size

    operator fun get(position: Int): ApiMovie = results[position]
}

data class ApiMovie(
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("genre_ids") val genreIds: List<Int>?
)

data class ApiGenresMovies(
    @SerializedName("genres") val genres: List<ApiGenreMovie>
)
data class ApiGenreMovie(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)