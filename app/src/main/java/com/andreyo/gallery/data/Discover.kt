package com.andreyo.gallery.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Discover(
    @SerializedName("page")
    @Expose
    var page: Int? = null,
    @SerializedName("results")
    @Expose
    var results: List<Film>? = null,
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null,
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null


)
