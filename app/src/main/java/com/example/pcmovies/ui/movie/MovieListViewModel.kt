package com.example.pcmovies.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pcmovies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    repository: MovieRepository
) : ViewModel() {

    val movies = repository.getTrendingMovies().cachedIn(viewModelScope)

}
