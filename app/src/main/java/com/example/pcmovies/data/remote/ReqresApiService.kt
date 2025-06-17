package com.example.pcmovies.data.remote

import com.example.pcmovies.data.model.CreateUserResponse
import com.example.pcmovies.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReqresApiService {

    @GET("api/users")
    suspend fun getUsers(@Query("page") page: Int): UserResponse

    @POST("api/users")
    suspend fun createUser(@Body body: Map<String, String>): Response<CreateUserResponse>

}

