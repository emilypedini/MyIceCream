package com.example.myicecream.data.repositories

import com.example.myicecream.data.database.NotificationDAO
import com.example.myicecream.data.database.NotificationEntity
import kotlinx.coroutines.flow.Flow

class NotificationRepository(private val notificationDAO: NotificationDAO) {

    suspend fun notifyUser(userId: Int, title: String, message: String){
        notificationDAO.insertNotification(NotificationEntity(
            userId = userId, title = title, message = message))
    }

    fun getNotifications(userId: Int): Flow<List<NotificationEntity>> {
        return notificationDAO.getUserNotifications(userId)
    }

    suspend fun markNotiAsRead(idNot: Int) {
        notificationDAO.markAsRead(idNot)
    }

    suspend fun deleteNotification(idNot: Int) {
        notificationDAO.deleteNotification(idNot)
    }

    fun getUnreadCount(userId: Int): Flow<Int> {
        return notificationDAO.getUnreadCount(userId)
    }
}