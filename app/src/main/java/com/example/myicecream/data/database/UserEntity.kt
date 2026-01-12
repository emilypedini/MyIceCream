package com.example.myicecream.data.database

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users",
    indices = [Index(value = ["nickname"], unique = true)]
)
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val surname: String,

    val nickname: String,

    val email: String,

    val password: String?,

    val phone: String?,

    val googleId: String?,

    val profileImagePath: String?,

    val screenTheme: Boolean
)

