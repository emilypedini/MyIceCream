package com.example.myicecream.data.repositories

import com.example.myicecream.data.database.UserDAO
import com.example.myicecream.data.database.UserEntity

data class UserRepository(private val userDAO: UserDAO) {

    suspend fun updateUserProfile(id: Int, name: String, surname: String, nickname: String, profileImagePath: String, screenTheme: Boolean) {
        return userDAO.updateUserName(id, name, surname, nickname, profileImagePath, screenTheme)
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

    suspend fun getUserById(id: Int) : UserEntity {
      return userDAO.getUserById(id)
    }

    suspend fun isNicknameTaken(nickname: String, userId: Int): Boolean {
        val user = userDAO.getUserByNickname(nickname)
        return user != null && user.id != userId
    }

    suspend fun searchUsersByNickname(query: String): List<UserEntity> {
        if (query.isBlank()) return emptyList()
        return userDAO.searchUsersByNickname(query)
    }
}
