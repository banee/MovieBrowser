package com.hermanek.moviebrowserdemo.model

data class Movie(
    val adult: Boolean,
    var backdrop_path: String?,
    val belongs_to_collection: BelongsToCollection?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String?,
    var original_title: String?,
    val overview: String?,
    val popularity: Double?,
    var poster_path: String?,
    val production_companies: List<ProductionCompany>?,
    val production_countries: List<ProductionCountry>?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    var title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)