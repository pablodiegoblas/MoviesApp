package com.example.mymoviesapp.data.server

import com.google.gson.annotations.SerializedName

data class ResponseModel(val results: MutableList<ApiMovie>) {

    val size: Int get() = results.size

    operator fun get(position: Int): ApiMovie = results[position]
}

data class ApiMovie(
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("vote_count") val voteCount: Int? = null,
    @SerializedName("genre_ids") val genreIds: List<Int>? = null
)

data class ApiGenresMovies(
    @SerializedName("genres") val genres: List<ApiGenreMovie>
)
data class ApiGenreMovie(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)