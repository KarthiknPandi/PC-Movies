package com.example.pcmovies.data.repository

import com.example.pcmovies.data.model.CreateUserResponse
import com.example.pcmovies.data.remote.ReqresApiService
import com.example.pcmovies.data.local.UserDao
import com.example.pcmovies.data.local.UserEntity
import retrofit2.Response
import javax.inject.Inject

class NewUserRepository @Inject constructor(
    private val reqresApiService: ReqresApiService,
    private val userDao: UserDao
) {

    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)

    suspend fun createUserOnline(name: String, job: String): Response<CreateUserResponse> {
        return reqresApiService.createUser(mapOf("name" to name, "job" to job))
    }

    suspend fun getUnsyncedUsers() = userDao.getUnsyncedUsers()

    suspend fun getAllUsers() = userDao.getAllUsers()

    suspend fun updateUser(user: UserEntity) = userDao.updateUser(user)


}

