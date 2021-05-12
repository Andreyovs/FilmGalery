package com.andreyo.gallery.helper

import android.content.Context
import android.util.Log
import com.andreyo.gallery.R
import com.andreyo.gallery.data.Film


class FilmHelper {
    companion object {
        const val ID = "id"
        var isFirstRun: Boolean = true
        lateinit var instance: FilmHelper
            private set
    }
    lateinit var checked: MutableList<Film>
    lateinit var liked: MutableList<Film>
    private lateinit var films: MutableList<Film>
    var page: Int = 1


    fun init() {
            instance = this
            instance.checked = mutableListOf()
            instance.liked = mutableListOf()
            instance.films = mutableListOf()
            isFirstRun = false
    }

    fun isFavorite(filmId: Int): Boolean {
        return instance.liked.contains(filmId)

    }

    fun getUrlByPostrPath(poster_path: String, ctx: Context): String {
        return ctx.getString(R.string.film_url) + poster_path
    }


    fun setFilms(filmList: List<Film>) {
        instance.films = filmList.toMutableList()
    }

    fun getFilms(): List<Film> {
        isFirstRun = false
        return instance.films
    }

    fun getFavoriteFilms(): MutableList<Film> {
            return instance.liked
    }


    fun getFilm(id: Int?): Film {
        Log.i("read id", id.toString())
        return instance.films.first { it.id == id }

    }

}



