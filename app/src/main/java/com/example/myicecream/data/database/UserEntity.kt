package com.example.myicecream.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val surname: String,

    val email: String,

    val password: String?,

    val phone: String?,

    val googleId: String?,

    val profileImagePath: String?
)
