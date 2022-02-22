package com.example.wembleymoviesapp.data.model

import com.google.gson.annotations.SerializedName

data class ResponseModel(val results: List<MovieModel>) {

    val size: Int get() = results.size

    operator fun get(position: Int): MovieModel = results[position]
}

data class MovieModel(

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("adult")
    val adult: Boolean? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null


) {
    override fun equals(other: Any?): Boolean {
        other as MovieModel

        if (this.id == other.id) return true

        return false
    }
}
