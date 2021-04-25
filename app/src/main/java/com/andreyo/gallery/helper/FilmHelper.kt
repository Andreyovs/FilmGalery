package com.andreyo.gallery.helper

import android.content.Context
import android.util.Log
import com.andreyo.gallery.R
import com.andreyo.gallery.data.Film
import com.andreyo.gallery.data.FilmResults
import com.google.gson.Gson
import java.io.InputStream


object FilmHelper {


    const val ID = "id"
    private lateinit var films: List<Film>
    lateinit var checked: MutableList<Int>
    lateinit var liked: MutableList<Int>

    fun isFavorite(filmId: Int): Boolean {
        return liked.contains(filmId)

    }

    fun getUrlByPostrPath(poster_path: String, ctx: Context): String {
        return ctx.getString(R.string.film_url) + poster_path
    }

    fun getFilms(): List<Film> {
        if (!this::films.isInitialized) {
            val dataIS: InputStream =
                this.javaClass.getResource("/res/raw/films.json").openStream()
            val strJson = dataIS.bufferedReader().use { it.readText() }
            dataIS.close()
            films = Gson().fromJson(strJson, FilmResults::class.java).results
            checked = mutableListOf()
            liked = mutableListOf()
        }
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



