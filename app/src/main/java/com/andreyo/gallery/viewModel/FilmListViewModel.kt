package com.andreyo.gallery.viewModel

import androidx.lifecycle.MutableLiveData
import com.andreyo.gallery.data.Discover
import com.andreyo.gallery.data.Film

class FilmListViewModel: BaseViewModel() {
    var filmsLiveData= MutableLiveData<List<Film>>()
    var filmListData = MutableLiveData<Event<Discover>>()
    private var favoriteFilmLiveData = MutableLiveData<List<Film>>()
    private var selectedFilmListData = MutableLiveData<List<Film>>()
    val API_KEY = "b1df320d88a0de6cc7bf96839ff29b9a"
    val sortBy = "popularity.desc"


    init {
        var films: MutableList<Film> = mutableListOf()
        filmsLiveData.postValue(films)
        favoriteFilmLiveData.postValue(films)
        selectedFilmListData.postValue(films)

    }

    fun getFilms(page: Int) {
        requestWithLiveData(filmListData) {
            api.getCurrentTopFilms(
                page = page,
                apiKey = API_KEY,
                sortBy = sortBy)
        }
        if (filmListData.value!=null) {
            filmsLiveData.postValue(filmListData.value!!.data!!.results!!.toMutableList())
        }

    }


}