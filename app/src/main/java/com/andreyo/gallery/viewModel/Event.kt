package com.andreyo.gallery.viewModel


data class Event<out T>(val status: Status, val data: T?, val error: String) {

    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null, "")
        }

        fun <T> success(data: T?): Event<T> {
            return Event(Status.SUCCESS, data, "")
        }

        fun <T> error(error: String): Event<T> {
            return Event(Status.ERROR, null, error)
        }
    }
}


