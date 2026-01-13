package com.example.myicecream.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val idNot: Int = 0,

    val userId: Int?,
    val title: String,
    val  message: String,

    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
