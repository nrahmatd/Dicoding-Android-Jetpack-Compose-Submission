package com.nrahmatd.moviecatalogue.data

import com.nrahmatd.moviecatalogue.model.MovieData
import com.nrahmatd.moviecatalogue.model.MovieModel

class MovieRepository {
    fun getMovies(): List<MovieModel> {
        return MovieData.getMovieList()
    }

    fun searchMovies(query: String): List<MovieModel> {
        return MovieData.getMovieList().filter {
            it.titleMovie.contains(query, ignoreCase = true)
        }
    }
}