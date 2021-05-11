package com.andreyo.gallery.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("discover/movie")
    suspend fun getCurrentTopFilms(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ): Response<Discover>

}
