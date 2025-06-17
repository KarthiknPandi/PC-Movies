package com.example.pcmovies.data.repository

import com.example.pcmovies.data.model.MovieDetail
import com.example.pcmovies.data.remote.TmdbApiService
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    private val apiService: TmdbApiService
) {
    suspend fun getMovieDetail(movieId: Int, apiKey: String): MovieDetail {
        return apiService.getMovieDetail(movieId, apiKey)
    }
}