package com.example.myicecream.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDAO {

    @Insert
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM notifications WHERE userId = :userId OR userId IS NULL ORDER BY createdAt DESC")
    fun getUserNotifications(userId: Int): Flow<List<NotificationEntity>>

    @Query("UPDATE notifications SET isRead = 1 WHERE idNot = :idNot")
    suspend fun markAsRead(idNot: Int)

    @Query("DELETE FROM notifications WHERE idNot = :idNot")
    suspend fun deleteNotification(idNot: Int)

    @Query("SELECT COUNT(*) FROM notifications WHERE userId = :userId AND isRead = 0")
    fun getUnreadCount(userId: Int): Flow<Int>
}