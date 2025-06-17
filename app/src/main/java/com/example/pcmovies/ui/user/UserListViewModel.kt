package com.example.pcmovies.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.pcmovies.data.remote.ReqresApiService
import com.example.pcmovies.data.remote.UserPagingSource
import com.example.pcmovies.data.repository.NewUserRepository
import com.example.pcmovies.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    userRepository: UserRepository,
    private val newUserRepository: NewUserRepository,
    private val reqresApiService: ReqresApiService,
) : ViewModel() {

    private val refreshTrigger = MutableStateFlow(Unit)

   // val users = userRepository.getUsers().flow.cachedIn(viewModelScope)


//    val users = refreshTrigger.flatMapLatest {
//        userRepository.getUsers().flow
//    }.cachedIn(viewModelScope)

    val users = refreshTrigger.flatMapLatest {
        Pager(
            config = PagingConfig(pageSize = 6),
            pagingSourceFactory = {
                UserPagingSource(reqresApiService, newUserRepository)
            }
        ).flow
    }.cachedIn(viewModelScope)


    fun refreshUsers() {
        refreshTrigger.value = Unit
    }
}

