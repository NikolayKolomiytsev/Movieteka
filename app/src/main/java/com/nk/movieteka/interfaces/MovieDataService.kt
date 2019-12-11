package com.nk.movieteka.interfaces

import com.nk.movieteka.models.MovieResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataService {

        @GET("discover/movie?api_key=c33e25174af866c5c102772d92d0e480&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false")
        fun moviesList(@Query("page") page: Int): Call<MovieResponseModel>

        @GET("search/movie?api_key=c33e25174af866c5c102772d92d0e480&language=en-US&include_adult=true")
        fun moviesListSearch(@Query("query") query: String,@Query("page") page: Int): Call<MovieResponseModel>
}