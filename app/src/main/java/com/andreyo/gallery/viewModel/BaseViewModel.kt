package com.andreyo.gallery.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreyo.gallery.data.NetworkService
import com.andreyo.gallery.data.ResponseWrapper
import com.andreyo.gallery.data.TmdbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {

    var api: TmdbApi = NetworkService.retrofitService()


   suspend fun <T> requestWithLiveData(
        liveData: MutableLiveData<Event<T>>,
        request: suspend() -> Response<T>
    ) {

        liveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                if (response.body() != null) {
                    liveData.postValue(Event.success(response.body()))
                } else if (response.errorBody() != null) {
                    liveData.postValue(Event.error(response.errorBody().toString()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(Event.error("null"))
            }
        }
    }

    suspend fun <T> requestWithCallback(
        request: suspend () -> ResponseWrapper<T>,
        response: (Event<T>) -> Unit) {

        response(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = request.invoke()

                launch(Dispatchers.Main) {
                    if (res.data != null) {
                        response(Event.success(res.data))
                    } else if (res.error != null) {
                        response(Event.error(res.error.toString()))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    response(Event.error("null"))
                }
            }
        }
    }
}