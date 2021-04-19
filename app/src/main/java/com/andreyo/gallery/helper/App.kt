package com.andreyo.gallery.helper

import android.app.Application
import android.util.Log
import com.andreyo.gallery.Film
import com.andreyo.gallery.FilmResults
import com.andreyo.gallery.data.Discover
import com.andreyo.gallery.data.TmdbApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App:Application() {
    private lateinit var api: TmdbApi
    lateinit var discover: Discover
    var films: MutableList<Film> = mutableListOf()

    companion object {
        lateinit var instance: App
            private set

        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "b1df320d88a0de6cc7bf96839ff29b9a"
        const val sortBy = "popularity.desc"

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initRetrofit()
        getTopMovies()
    }

    fun getTopMovies() {
       // tmdbApi.toString()
      api.getCurrentTopFilms(
            API_KEY,
            sortBy,
            1
        ).enqueue(object : Callback<Discover?> {
            override fun onFailure(call: Call<Discover?>, t: Throwable) {

                Log.i("Retrofit error", t.message.toString())
                discover = Discover()
            }

            override fun onResponse(call: Call<Discover?>, response: Response<Discover?>) {
                discover = response.body()!!
                populateFilms(discover)
            }
        })
    }

    private fun initRetrofit() {
        val client = OkHttpClient()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(TmdbApi::class.java)
    }

    private fun populateFilms(discover: Discover) {
        films = Gson().fromJson(discover.results?.toString(), FilmResults::class.java).results.toMutableList()

    }
}