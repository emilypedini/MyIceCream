package com.example.myicecream.data.repositories

import com.example.myicecream.data.database.UserDAO
import com.example.myicecream.data.database.UserEntity

class AuthRepository(private val userDAO: UserDAO) {

    suspend fun signUp(user: UserEntity): Boolean {
        val userExist = userDAO.getUserByEmail(user.email)
        return if (userExist == null){
            userDAO.insertUser(user)
            true
        }else{
            false
        }
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return userDAO.loginUser(email, password)
    }
}