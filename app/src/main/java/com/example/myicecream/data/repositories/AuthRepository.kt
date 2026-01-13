package com.example.myicecream.data.repositories

import com.example.myicecream.data.database.UserDAO
import com.example.myicecream.data.database.UserEntity

class AuthRepository(private val userDAO: UserDAO,
    private val notificationRepository: NotificationRepository) {

    suspend fun signUp(user: UserEntity): Boolean {
        val existingEmail = userDAO.getUserByEmail(user.email)
        if (existingEmail != null) {
            throw IllegalArgumentException("EMAIL_EXISTS")
        }
        val existingNickname = userDAO.getUserByNickname(user.nickname)
        if (existingNickname != null) {
            throw IllegalArgumentException("NICKNAME_EXISTS")
        }
        userDAO.insertUser(user)

        val insertedUser = userDAO.getUserByEmail(user.email) ?: throw IllegalArgumentException("errore")

        notificationRepository.notifyUser(
            userId = insertedUser.id,
            title = "Benvenuto \uD83D\uDD14",
            message = "${user.name}, ti diamo il nostro caloroso benvenuto." +
                    "Rimani con noi per scoprire in anteprima le novità." +
                    " Nuvole di Gelato."
        )

        return true
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return userDAO.loginUser(email, password)
    }
}