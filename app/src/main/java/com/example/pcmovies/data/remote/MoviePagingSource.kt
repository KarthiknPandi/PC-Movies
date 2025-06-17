package com.example.pcmovies.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pcmovies.data.model.Movie
import com.example.pcmovies.utils.Constants

class MoviePagingSource(
    private val apiService: TmdbApiService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getTrendingMovies(
                page = page,
                apiKey = Constants.TMDB_API_KEY
            )

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}

