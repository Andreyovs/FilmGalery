package com.andreyo.gallery.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.andreyo.gallery.FilmAdapter
import com.andreyo.gallery.R
import com.andreyo.gallery.data.Discover
import com.andreyo.gallery.data.Film
import com.andreyo.gallery.helper.FilmHelper
import com.andreyo.gallery.viewModel.Status
import com.andreyo.gallery.viewModel.filmListViewModel


class FilmListFragment : Fragment() {
    companion object {
        const val TAG = "FilmListFragment"
    }
    private lateinit var am:filmListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rv: RecyclerView = view.findViewById(R.id.rv_Films)
        var fm= FilmHelper()
        fm.initFilms()
        am = ViewModelProvider(this).get(filmListViewModel::class.java)//ViewModelProvider.NewInstanceFactory().create(filmListViewModel::class.java) //..of(this).get(ActivityViewModel::class.java)
        observeGetFilms()
        rv.adapter = FilmAdapter(FilmHelper.instance.getFilms(), object : FilmAdapter.Callback {
            override fun onItemClicked(item: Film) {

            }
        }, LayoutInflater.from(requireContext()))
        rv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    FilmHelper.page++
                    FilmHelper.instance.getTopFilms(FilmHelper.page)
                    recyclerView.post {
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                }
            }
        })

    }

    private fun observeGetFilms() {
        am.getFilms(1) {
            when (it.status) {
                Status.LOADING -> viewTwoLoading()
                Status.SUCCESS -> viewTwoSuccess(it.data)
                Status.ERROR -> viewTwoError(it.error)
            }
        }
    }
    private fun viewTwoLoading() {

    }

    private fun viewTwoSuccess(data: Discover?) {
    Log.i("Found", data!!.results!!.toMutableList().toString())
    }

    private fun viewTwoError(error: Error?) {

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

}