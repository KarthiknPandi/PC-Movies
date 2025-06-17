package com.example.pcmovies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pcmovies.data.model.Movie
import com.example.pcmovies.data.remote.MoviePagingSource
import com.example.pcmovies.data.remote.TmdbApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val tmdbApiService: TmdbApiService
) {
    fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviePagingSource(
                    tmdbApiService
                )
            }
        ).flow
    }
}


