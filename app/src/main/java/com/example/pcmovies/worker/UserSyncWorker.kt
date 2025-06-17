package com.example.pcmovies.worker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserSyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val repository = ServiceLocator.userRepository

    override suspend fun doWork(): Result {
        val unsyncedUsers = repository.getUnsyncedUsers()
        unsyncedUsers.forEach { user ->
            val response = repository.createUserOnline(user.name, user.job)
            if (response.isSuccessful) {
                Log.e("SyncWorker", "Synced user: ${user.name}")
                showToast("Synced user: ${user.name}")
                response.body()?.let {
                    val syncedUser = user.copy(serverId = it.id, isSynced = true)
                    repository.insertUser(syncedUser)
                }
            }else{
                Log.e("SyncWorker", "Sync user: ${user.name} failed")
                showToast("Sync user: ${user.name} failed")
            }
        }
        return Result.success()
    }

    private suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
        }
    }
}

