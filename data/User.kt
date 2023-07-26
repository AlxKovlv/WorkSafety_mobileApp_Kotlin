package com.example.taladapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val username: String,
    val email: String,
    val password: String,
    val isAdmin: Boolean = false
)

