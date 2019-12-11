package com.nk.movieteka.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nk.movieteka.helpers.MovieRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var page = 1
    var query = ""

    private val movieRepository: MovieRepository

    val allPopularMovies: LiveData<List<MovieModel>>
        get() = movieRepository.getMutableLiveDataPopularFilms(page)

    val searchMovies: LiveData<List<MovieModel>>
        get() = movieRepository.getMutableLiveDataSearchFilms(query,page)

    init {
        movieRepository = MovieRepository()
    }
}