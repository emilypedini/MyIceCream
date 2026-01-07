package com.example.myicecream.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "posts",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PostEntity(

    @PrimaryKey(autoGenerate = true)
    val idPost: Int = 0,

    val userId: Int,

    val postImageUri: String,

    val description: String,

    val position: String? = null,

    val createdAt: Long = System.currentTimeMillis()
)
