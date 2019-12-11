package com.nk.movieteka.models

import com.google.gson.annotations.SerializedName

class MovieModel {

    @SerializedName("title")
    var title: String? = null
    @SerializedName("overview")
    var overivew: String? = null
    @SerializedName("backdrop_path")
    var backdrop_path: String? = null
}