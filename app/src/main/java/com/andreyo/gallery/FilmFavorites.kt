package com.andreyo.gallery

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FilmFavorites : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.film_list_favorite)
        var recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerViewFavorite)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FavoriteAdapter(FilmHelper.getFavoriteFilms(), object : FavoriteAdapter.Callback {
            override fun onItemClicked(item: Film) {

            }
            override fun onDeleteClick(position: Int) {
                recyclerView.adapter?.notifyItemRemoved(position)
                recyclerView.adapter?.notifyItemRangeChanged(position, FilmHelper.liked.size)
                Log.i("adapter size : ",recyclerView.adapter?.itemCount.toString())
                Log.i("favorite size : ",FilmHelper.liked.size.toString())
                Log.i("favorite procedure size : ",FilmHelper.getFavoriteFilms().size.toString())




            }
        }, this)

    }

}


