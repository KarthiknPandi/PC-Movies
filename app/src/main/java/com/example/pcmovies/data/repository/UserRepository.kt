package com.example.pcmovies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.pcmovies.data.model.User
import com.example.pcmovies.data.remote.ReqresApiService
import com.example.pcmovies.data.remote.UserPagingSource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val reqresApiService: ReqresApiService,
    private val newUserRepository: NewUserRepository
) {
    fun getUsers(): Pager<Int, User> {
        return Pager(
            config = PagingConfig(pageSize = 6),
            pagingSourceFactory = { UserPagingSource(reqresApiService, newUserRepository) }
        )
    }
}
