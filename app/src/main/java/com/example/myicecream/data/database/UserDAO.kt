package com.example.myicecream.data.database

import android.adservices.adid.AdId
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): UserEntity?

    @Query("""UPDATE users SET name = :name,surname = :surname, profileImagePath = :profileImagePath  WHERE id = :userId""")
    suspend fun updateUserName(userId: Int, name: String, surname: String, profileImagePath: String)

    @Query("""UPDATE users SET password = :password WHERE id = :userId""")
    suspend fun updatePassword(userId: Int, password: String)

    @Query("UPDATE users SET profileImagePath = :path WHERE id = :id")
    suspend fun updateProfileImage(id: Int, path: String)

    @Query("SELECT profileImagePath FROM users WHERE id = :id")
    suspend fun getProfileImageById(id: Int): String?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity


}