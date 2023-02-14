package com.nrahmatd.moviecatalogue.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nrahmatd.moviecatalogue.data.MovieRepository
import com.nrahmatd.moviecatalogue.model.MovieModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movies = MutableStateFlow(
        repository.getMovies()
    )
    val movies: StateFlow<List<MovieModel>> get() = _movies

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _movies.value = repository.searchMovies(_query.value)
    }
}

class ViewModelFactory(private val repository: MovieRepository) :
        ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}