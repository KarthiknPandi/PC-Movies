package com.example.pcmovies.data.model


data class User(
    val id: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String,
    val isSynced: Boolean = true,
    val job : String
)

data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
)
