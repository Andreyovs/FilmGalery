package com.andreyo.gallery.viewModel

import androidx.lifecycle.MutableLiveData
import com.andreyo.gallery.data.Discover
import com.andreyo.gallery.data.Film

class filmListViewModel: BaseViewModel() {
    var filmListData = MutableLiveData<Event<Discover>>()
    private var favoriteFilmListData = MutableLiveData<Event<Discover>>()
    private var selectedFilmListData = MutableLiveData<Event<Film>>()
    private var errorLData = MutableLiveData<String>()
    val API_KEY = "b1df320d88a0de6cc7bf96839ff29b9a"
    val sortBy = "popularity.desc"

    fun getFilms(page: Int,callback: (data: Event<Discover>) -> Unit) {
       requestWithCallback({
            api.getCurrentTopFilms(
                page = page,
                apiKey = API_KEY,
                sortBy = sortBy)
        }){
           callback(it)
       }
    }
    fun moreFilms() {
        //FilmHelper.page++
        //val message = FilmHelper.instance.getTopFilms(FilmHelper.page)

        /*if (App.instance.error) {
            App.page--
            errorLiveData.postValue(message)
        } else moviesLiveData.value = App.instance.items*/
    }


}