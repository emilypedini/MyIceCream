package com.example.myicecream.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDAO {

    @Insert
    suspend fun insertPost(post: PostEntity)

    @Query("""
    SELECT 
        p.idPost,
        p.description,
        p.postImageUri,
        p.createdAt,
        u.id AS userId,
        u.name,
        u.surname,
        u.profileImagePath
    FROM posts p
    INNER JOIN users u ON p.userId = u.id
    ORDER BY p.createdAt DESC
""")
    suspend fun getAllPosts(): List<PostWithUser>

    @Query("SELECT * FROM posts WHERE userId = :userId ORDER BY createdAt DESC")
    suspend fun getPostByUser(userId: Int): List<PostEntity>

    @Query("DELETE FROM posts WHERE idPost = :idPost")
    suspend fun deletePost(idPost: Int)
}