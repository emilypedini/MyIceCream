package com.example.myicecream.data.database

data class PostWithUser(
    val postId: Int,
    val description: String,
    val imageUri: String,
    val createdAt: Long,

    val userId: Int,
    val name: String,
    val surname: String,
    val profileImagePath: String?
)
