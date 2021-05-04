package com.andreyo.gallery.helper

import android.app.Application
import android.content.Context
import android.util.Log
import com.andreyo.gallery.R
import com.andreyo.gallery.data.Discover
import com.andreyo.gallery.data.Film
import com.andreyo.gallery.data.TmdbApi


class FilmHelper : Application() {


    val ID = "id"
    private lateinit var films: MutableList<Film>
    lateinit var checked: MutableList<Film>
    lateinit var liked: MutableList<Film>
    private lateinit var api: TmdbApi
    var isFirstRun: Boolean = true
    lateinit var discover: Discover

    companion object {
        lateinit var instance: FilmHelper
            private set
        val BASE_URL = "https://api.themoviedb.org/3/"
        val API_KEY = "b1df320d88a0de6cc7bf96839ff29b9a"
        val sortBy = "popularity.desc"
        var page = 1
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        /*initRetrofit()
        getTopFilms(page)*/
    }

    fun getTopFilms(page: Int) {
/*
        api.getCurrentTopFilms(
            API_KEY,
            sortBy,
            page
        ).enqueue(object : Callback<Discover?> {
            override fun onFailure(call: Call<Discover?>, t: Throwable) {

                Log.i("Retrofit error", t.message.toString())
                discover = Discover()
            }

            override fun onResponse(call: Call<Discover?>, response: Response<Discover?>) {
                discover = response.body()!!
                populateFilms(discover.results!!.toMutableList())
            }
        })*/
    }

    private fun initRetrofit() {
  /*      val client = OkHttpClient()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


        api = retrofit.create(TmdbApi::class.java)
   */
    }


    private fun populateFilms(results: MutableList<Film>) {
        films = results

    }

    fun isFavorite(filmId: Int): Boolean {
        return liked.contains(filmId)

    }

    fun getUrlByPostrPath(poster_path: String, ctx: Context): String {
        return ctx.getString(R.string.film_url) + poster_path
    }

    fun initFilms(): List<Film> {
        instance = this

        //if (!this::films.isInitialized) {
           // initRetrofit()

            //getTopFilms(page)
            films = mutableListOf()

            checked = mutableListOf()
            liked = mutableListOf()
        //}
        isFirstRun = false
        return films
    }

    fun getFilms(): List<Film> {
        films = mutableListOf()
        return films
    }

    fun getFavoriteFilms(): MutableList<Film> {
        if (liked.size > 0) {
            return films.filter { it.id in liked }.toMutableList()

        }
        return mutableListOf()
    }


    fun getFilm(id: Int?): Film {
        Log.i("read id", id.toString())
        return films.first { it.id.equals(id) }

    }

}



