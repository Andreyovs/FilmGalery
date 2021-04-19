package com.andreyo.gallery.helper

import android.content.Context
import android.util.Log
import com.andreyo.gallery.Film
import com.andreyo.gallery.FilmResults
import com.andreyo.gallery.R
import com.andreyo.gallery.data.TmdbApi
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStream




object FilmHelper {


        const val ID = "id"
        private lateinit var films: List<Film>
        public  lateinit var checked :MutableList<Int>
        public  lateinit var liked :MutableList<Int>

        public fun isFavorite(filmId: Int):Boolean{
            return liked.contains(filmId)

        }
        public fun GetUrlByPostrPath(poster_path: String,ctx : Context ):String{
            return ctx.getString(R.string.film_url) + poster_path
        }
        public fun getFilms(): List<Film> {
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

        public fun getFavoriteFilms(): MutableList<Film> {
            if (liked.size>0) {
               return films.filter {it.id in liked }.toMutableList()

            }
            return mutableListOf()
        }


        public fun getFilm(id: Int?): Film {
            Log.i("read id", id.toString())
            return films.filter { it.id.equals(id) }.first()

        }

    }
object Apifactory{
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "b1df320d88a0de6cc7bf96839ff29b9a"
    const val sortBy = "popularity.desc"
    //Creating Auth Interceptor to add api_key query in front of all the requests.
    private val authInterceptor = Interceptor {chain->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    //OkhttpClient for building http request url
    private val tmdbClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()



    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(tmdbClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    val tmdbApi : TmdbApi = retrofit().create(TmdbApi::class.java)

}


