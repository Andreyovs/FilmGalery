package com.andreyo.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    lateinit var films: List<Film>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recilerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FilmAdapter(FilmHelper.getFilms(), object : FilmAdapter.Callback {
            override fun onItemClicked(item: Film) {
            }
        }, this)

    }


}



