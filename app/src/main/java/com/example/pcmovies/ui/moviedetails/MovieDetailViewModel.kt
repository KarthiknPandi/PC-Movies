package com.example.pcmovies.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcmovies.data.model.MovieDetail
import com.example.pcmovies.data.repository.MovieDetailRepository
import com.example.pcmovies.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository
) : ViewModel() {

    private val _movieDetail = MutableStateFlow(MovieDetail(0, "", "", "", ""))
    val movieDetail: StateFlow<MovieDetail> = _movieDetail

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            val detail = repository.getMovieDetail(movieId, Constants.TMDB_API_KEY)
            _movieDetail.value = detail
        }
    }
}