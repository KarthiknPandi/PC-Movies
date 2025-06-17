package com.example.pcmovies.ui.newuser

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcmovies.data.repository.NewUserRepository
import com.example.pcmovies.data.local.UserEntity
import com.example.pcmovies.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val newUserRepository: NewUserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<AddUserEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun addUser(name: String, job: String) {
        viewModelScope.launch {
            try {
                if (NetworkUtils.isOnline(context)) {
                    val response = newUserRepository.createUserOnline(name, job)
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            val user = UserEntity(
                                name = res.name,
                                job = res.job,
                                serverId = res.id,
                                isSynced = true
                            )
                            newUserRepository.insertUser(user)
                            _eventFlow.emit(AddUserEvent.Success("User synced and saved!"))
                        } ?: _eventFlow.emit(AddUserEvent.Failure("Empty response body"))
                    } else {
                        _eventFlow.emit(AddUserEvent.Failure("API Error: ${response.code()}"))
                    }
                } else {
                    val user = UserEntity(name = name, job = job)
                    newUserRepository.insertUser(user)
                    _eventFlow.emit(AddUserEvent.Success("User saved offline!"))
                }
            } catch (e: Exception) {
                _eventFlow.emit(AddUserEvent.Failure("Something went wrong: ${e.localizedMessage}"))
            }
        }
    }

    sealed class AddUserEvent {
        data class Success(val message: String) : AddUserEvent()
        data class Failure(val message: String) : AddUserEvent()
    }
}


