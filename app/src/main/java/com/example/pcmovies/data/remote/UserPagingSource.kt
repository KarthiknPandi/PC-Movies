package com.example.pcmovies.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pcmovies.data.model.User
import com.example.pcmovies.data.repository.NewUserRepository

class UserPagingSource(
    private val reqresApiService: ReqresApiService,
    private val newUserRepository: NewUserRepository
) : PagingSource<Int, User>() {

//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
//        Log.e("UserPagingSource", "load called with params: $params")
//        return try {
//            val currentPage = params.key ?: 1
//            val response = apiService.getUsers(currentPage)
//
//            Log.e("UserPagingSource", "Response: $response")
//
//            LoadResult.Page(
//                data = response.data,
//                prevKey = if (currentPage == 1) null else currentPage - 1,
//                nextKey = if (currentPage >= response.total_pages) null else currentPage + 1
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1
        Log.e("test", "Paging source called for page $page")

        return try {
            val localUsers = if (page == 1) {
                newUserRepository.getAllUsers().map {
                    User(
                        id = it.serverId!!,
                        first_name = it.name,
                        last_name = "",
                        email = "",
                        avatar = "",
                        job = it.job,
                        isSynced = it.isSynced
                    )
                }
            } else emptyList()

            val apiUsers = try {
                val response = reqresApiService.getUsers(page)
                response.data.map {
                    User(
                        id = it.id,
                        first_name = it.first_name,
                        last_name = it.last_name,
                        job = "From API",
                        isSynced = true,
                        avatar = it.avatar,
                        email = it.email
                    )
                }
            } catch (e: Exception) {
                emptyList()
            }

            val combined = localUsers + apiUsers

            val isEndOfPagination = apiUsers.isEmpty()

            LoadResult.Page(
                data = combined,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (isEndOfPagination) null else page + 1
            )

        } catch (e: Exception) {
            Log.e("test", "Load error: ${e.localizedMessage}")
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition
    }
}
