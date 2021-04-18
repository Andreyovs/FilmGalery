package com.andreyo.gallery.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.andreyo.gallery.FavoriteAdapter
import com.andreyo.gallery.Film
import com.andreyo.gallery.R
import com.andreyo.gallery.helper.FilmHelper

class FilmListFavoritesFragment: Fragment() {
        companion object{
        const val TAG = "FilmListFavoritesFragment"
    }
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_film_list_favorites, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            var rv: RecyclerView = view.findViewById(R.id.rv_Favorite)
            rv.adapter = FavoriteAdapter(FilmHelper.getFavoriteFilms(), object : FavoriteAdapter.Callback {
                override fun onItemClicked(item: Film) {

                }
                override fun onDeleteClick(position: Int) {
                    rv.adapter?.notifyItemRemoved(position)
                    rv.adapter?.notifyItemRangeChanged(position, FilmHelper.liked.size)
                    Log.i("adapter size : ",rv.adapter?.itemCount.toString())
                    Log.i("favorite size : ", FilmHelper.liked.size.toString())
                    Log.i("favorite procedure size : ", FilmHelper.getFavoriteFilms().size.toString())




                }
            }, LayoutInflater.from(requireContext()))
            rv.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

        }
}