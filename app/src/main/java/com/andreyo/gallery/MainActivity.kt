package com.andreyo.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    private lateinit var data: InputStream
    lateinit var films: List<Film>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = resources.openRawResource(R.raw.films)
        var recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recilerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FilmAdapter(fillList(), object : FilmAdapter.Callback {
            override fun onItemClicked(item: Film) {
            }
        }, this)
    }

    private fun fillList(): List<Film> {
        val strJson = data.bufferedReader().use { it.readText() }
        films = Gson().fromJson(strJson, Film_Results::class.java).results
        return films

    }
}



