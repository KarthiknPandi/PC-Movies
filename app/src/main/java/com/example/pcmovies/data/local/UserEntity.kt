package com.example.pcmovies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val localId: Int = 0,
    val name: String,
    val job: String,
    val serverId: String? = "-1",
    val isSynced: Boolean = false
)
