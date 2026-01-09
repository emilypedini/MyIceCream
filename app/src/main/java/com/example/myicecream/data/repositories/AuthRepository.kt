package com.example.myicecream.data.repositories

import com.example.myicecream.data.database.UserDAO
import com.example.myicecream.data.database.UserEntity

class AuthRepository(private val userDAO: UserDAO) {

    suspend fun signUp(user: UserEntity): Boolean {
        val existingEmail = userDAO.getUserByEmail(user.email)
        if (existingEmail != null) {
            throw IllegalArgumentException("EMAIL_EXISTS")
        }
        val existingNickname = userDAO.getUserByNickaname(user.nickname)
        if (existingNickname != null) {
            throw IllegalArgumentException("NICKNAME_EXISTS")
        }
        userDAO.insertUser(user)
        return true
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return userDAO.loginUser(email, password)
    }
}