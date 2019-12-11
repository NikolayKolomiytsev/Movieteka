package com.nk.movieteka.helpers

import androidx.lifecycle.MutableLiveData
import com.nk.movieteka.models.MovieModel
import com.nk.movieteka.models.MovieResponseModel

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger

class MovieRepository {

    private var movies = ArrayList<MovieModel>()
    private val mutableLiveData = MutableLiveData<List<MovieModel>>()

    fun getMutableLiveDataPopularFilms(page : Int): MutableLiveData<List<MovieModel>> {
        val userDataService = RetrofitClient.service
        val call = userDataService.moviesList(page)
        call.enqueue(object : Callback<MovieResponseModel> {
            override fun onResponse(
                call: Call<MovieResponseModel>,
                response: Response<MovieResponseModel>
            ) {
                val movieDBResponse = response.body()
                run {
                    if (movieDBResponse != null) {
                        movies.addAll(movieDBResponse.movieModel as ArrayList<MovieModel>)
                    }
                    mutableLiveData.setValue(movies)
                }
            }
            override fun onFailure(call: Call<MovieResponseModel>, t: Throwable) {}
        })
        return mutableLiveData
    }

    fun getMutableLiveDataSearchFilms(query : String, page : Int): MutableLiveData<List<MovieModel>> {
        val userDataService = RetrofitClient.service
        val call = userDataService.moviesListSearch(query,page)
        if(page == 1)
        {
            movies = ArrayList<MovieModel>()
        }
        call.enqueue(object : Callback<MovieResponseModel> {
            override fun onResponse(
                call: Call<MovieResponseModel>,
                response: Response<MovieResponseModel>
            ) {
                val movieDBResponse = response.body()
                Logger.getLogger(MovieRepository::class.java.name).warning(response.body()!!.total_results.toString())
                run {
                    if (movieDBResponse != null) {
                        movies.addAll(movieDBResponse.movieModel as ArrayList<MovieModel>)
                    }
                    mutableLiveData.setValue(movies)
                }
            }
            override fun onFailure(call: Call<MovieResponseModel>, t: Throwable) {}
        })
        return mutableLiveData
    }
}