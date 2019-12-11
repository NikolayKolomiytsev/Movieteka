package com.nk.movieteka.helpers

import com.nk.movieteka.interfaces.MovieDataService
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://api.themoviedb.org/3/"
    val service: MovieDataService
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!.create(MovieDataService::class.java)
        }
}