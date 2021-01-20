package com.andreyo.gallery

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.InputStream

class FilmDetails : AppCompatActivity() {
    private lateinit var data: InputStream

    companion object {
        const val ID = "id"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_details)
        data = resources.openRawResource(R.raw.films) //потом перенести
        val film :Film = getFilm(intent.getIntExtra(ID, 0))
        val filmName = this.findViewById<TextView>(R.id.filmName)
        val filmDescr = this.findViewById<TextView>(R.id.filmDescr)
        val filmAdult = this.findViewById<TextView>(R.id.filmAdult)
        filmName.text = film.title
        filmDescr.text = film.overview
        filmAdult.text = film.adult.toString()
    }

    private fun getFilm(id: Int): Film {
        val strJson = data.bufferedReader().use { it.readText() }
        val films: List<Film> = Gson().fromJson(strJson, Film_Results::class.java).results
        return films!!.filter {  it.id.equals(id) }.first()

    }
}