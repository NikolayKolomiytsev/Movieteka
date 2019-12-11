package com.nk.movieteka.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieResponseModel {

    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("total_results")
    @Expose
    var total_results: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    @SerializedName("results")
    @Expose
    var movieModel: List<MovieModel>? = null

}