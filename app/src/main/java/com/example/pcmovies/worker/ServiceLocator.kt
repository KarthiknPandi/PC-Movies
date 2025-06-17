package com.example.pcmovies.worker

import com.example.pcmovies.data.repository.NewUserRepository

object ServiceLocator {
    lateinit var userRepository: NewUserRepository

    fun init(repository: NewUserRepository) {
        userRepository = repository
    }
}
