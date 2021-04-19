package com.andreyo.gallery.data

import com.andreyo.gallery.Film
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TmdbApi {
    @GET("discover/movie")
    fun getCurrentTopFilms(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int


    ): Call<Discover>
}

data class Discover(
    @SerializedName("page")
    @Expose
    var page: Int? = null,
    @SerializedName("results")
    @Expose
    var results: List<Film>? = null,
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null,
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null


)