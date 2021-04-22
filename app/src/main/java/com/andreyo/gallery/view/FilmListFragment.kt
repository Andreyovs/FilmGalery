package com.andreyo.gallery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.andreyo.gallery.FilmAdapter
import com.andreyo.gallery.R
import com.andreyo.gallery.data.Film
import com.andreyo.gallery.helper.FilmHelper

class FilmListFragment : Fragment() {
    companion object{
        const val TAG = "FilmListFragment"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var rv: RecyclerView = view.findViewById(R.id.rv_Films)
        rv.adapter = FilmAdapter(FilmHelper.getFilms(), object : FilmAdapter.Callback {
            override fun onItemClicked(item: Film) {

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