package com.hermanek.moviebrowserdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigInteger

@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey
    var id: Int = -1,
    val adult: Boolean? = null,
    var backdrop_path: String? = null,
    val belongs_to_collection: BelongsToCollection? = null,
    val budget: Int? = null,
    val genres: List<Genre>? = null,
    val genre_ids: List<Int>? = null,
    val homepage: String? = null,
    val imdb_id: String? = null,
    val original_language: String? = null,
    var original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    var poster_path: String? = null,
    val production_companies: List<ProductionCompany>? = null,
    val production_countries: List<ProductionCountry>? = null,
    val release_date: String? = null,
    val revenue: BigInteger? = null,
    val runtime: Int? = null,
    val spoken_languages: List<SpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    var title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    var translation: String? = null
) : Serializable