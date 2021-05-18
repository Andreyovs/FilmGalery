package com.andreyo.gallery.helper

import android.app.Application
import android.content.Context
import android.util.Log
import com.andreyo.gallery.R
import com.andreyo.gallery.data.Film
import com.andreyo.gallery.db.FilmsDb
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.installations.FirebaseInstallations
import java.util.*


class FilmHelper: Application() {
    companion object {
        const val ID = "id"
        const val CHANNEL_ID = "MovieDB"
        var isFirstRun: Boolean = true
        lateinit var instance: FilmHelper
            private set
    }
    lateinit var  db: FilmsDb
    lateinit var checked: MutableList<Film>
    lateinit var liked: MutableList<Film>
    private lateinit var films: MutableList<Film>
    var page: Int = 1

    override fun onCreate() {
        super.onCreate()
        isFirstRun = false
        instance = this
        instance.checked = mutableListOf()
        instance.liked = mutableListOf()
        instance.films = mutableListOf()
        db = FilmsDb.getInstance(this.applicationContext)
            /*getFirebaseToken()

        setUserID().let {
            FirebaseCrashlytics.getInstance().setUserId(it)
        }*/
    }


    /*fun init(ctx:Context) {


    }*/
    private fun getFirebaseToken() {
        FirebaseInstallations.getInstance().id.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("AZTAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result

                // Log and toast
                val msg = token ?: ""
                Log.d("AZTAG", msg)
            })
    }
    private fun createUserId(): String = UUID.randomUUID().toString()

    private fun setUserID(): String {
        val sharedPreferences = getSharedPreferences("films_prefs", Context.MODE_PRIVATE)
        var userId: String = sharedPreferences.getString("userId", "")!!
        if (userId == "") {
            userId = createUserId()
            val editor = sharedPreferences.edit()
            editor.putString("userId", userId)
            editor.apply()
        }
        return userId
    }
    fun isFavorite(filmId: Int): Boolean {
        return this.liked.contains(filmId)

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



