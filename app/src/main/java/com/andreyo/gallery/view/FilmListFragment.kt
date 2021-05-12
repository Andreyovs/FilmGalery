package com.andreyo.gallery.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.andreyo.gallery.FilmAdapter
import com.andreyo.gallery.R
import com.andreyo.gallery.data.Discover
import com.andreyo.gallery.data.Film
import com.andreyo.gallery.helper.FilmHelper
import com.andreyo.gallery.view.FilmListFragment.Companion.TAG
import com.andreyo.gallery.viewModel.FilmListViewModel
import com.andreyo.gallery.viewModel.Status


class FilmListFragment : Fragment() {
    companion object {
        const val TAG = "FilmListFragment"
    }

    private lateinit var viewModel: FilmListViewModel
    private lateinit var rv: RecyclerView
    private lateinit var adapter: FilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv = view.findViewById(R.id.rv_Films)
        viewModel = ViewModelProvider(this).get(FilmListViewModel::class.java)
        observeGetFilms()
        if (FilmHelper.isFirstRun) {
            FilmHelper().init()
            viewModel.getFilms(FilmHelper.instance.page)
        }
        var films: MutableList<Film> = mutableListOf()
        if (FilmHelper.instance.getFilms().isNotEmpty()) {
            films = FilmHelper.instance.getFilms().toMutableList()
        } else if (!viewModel.filmsLiveData.value.isNullOrEmpty()) {
            films = viewModel.filmsLiveData.value as MutableList<Film>
        }

        adapter =
            FilmAdapter(films, object : FilmAdapter.Callback {
                override fun onItemClicked(item: Film) {

                }
            }, LayoutInflater.from(requireContext()))
        rv.adapter = adapter
        rv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    FilmHelper.instance.page++
                    viewModel.getFilms(FilmHelper.instance.page)
                    recyclerView.post {
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                }
            }
        })

    }

    private fun observeGetFilms() {
        viewModel.filmListData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> filmListDataLoading()
                Status.SUCCESS -> prepareData(it.data)
                Status.ERROR -> filmListDataError(it.error)
            }
        })
    }


    private fun prepareData(data: Discover?) {
        if (!data!!.results!!.isNullOrEmpty()) {
            var films: List<Film> = mutableListOf()
            films = FilmHelper.instance.getFilms() + data!!.results!!.toMutableList()
            //
            FilmHelper.instance.setFilms(films.distinct())
            adapter.setItems(films.distinct())
            adapter.notifyDataSetChanged()
        }
    }

    private fun filmListDataError(error: String) {
        Log.i(TAG, error)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

}

private fun filmListDataLoading() {
    Log.i(TAG, "filmListData is loading)")
}
