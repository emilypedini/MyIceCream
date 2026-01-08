package com.example.myicecream.data.repositories

import com.example.myicecream.data.database.UserDAO

data class UserRepository(private val userDAO: UserDAO) {

    suspend fun updateUserProfile(id: Int, name: String, surname: String, profileImagePath: String) {
        return userDAO.updateUserName(id, name, surname, profileImagePath)
    }

    suspend fun updatePassword(id: Int,  password: String) {
        return userDAO.updatePassword(id, password)
    }

    suspend fun updateProfileImage(id: Int, profileImagePath: String) {
        userDAO.updateProfileImage(id, profileImagePath)
    }

    suspend fun getUserProfileImage(id: Int): String? {
        return userDAO.getProfileImageById(id)
    }

}
