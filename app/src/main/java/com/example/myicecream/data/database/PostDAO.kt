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
        p.idPost AS postId,
        p.description,
        p.postImageUri AS imageUri,
        p.position,
        p.createdAt,
        u.id AS userId,
        u.nickname,
        u.name,
        u.surname,
        u.profileImagePath
    FROM posts p
    INNER JOIN users u ON p.userId = u.id
    ORDER BY p.createdAt DESC
    """)
    suspend fun getAllPosts(): List<PostWithUser>

    //@Query("SELECT * FROM posts WHERE userId = :userId ORDER BY createdAt DESC")
    //suspend fun getPostByUser(userId: Int): List<PostEntity>

    @Query("""
    SELECT 
        p.idPost AS postId,
        p.description,
        p.postImageUri AS imageUri,
        p.position,
        p.createdAt,
        u.id AS userId,
        u.nickname,
        u.name,
        u.surname,
        u.profileImagePath
    FROM posts p
    INNER JOIN users u ON p.userId = u.id
    WHERE p.userId = :userId
    ORDER BY p.createdAt DESC
    """)
    suspend fun getPostByUser(userId: Int): List<PostWithUser>

    @Query("DELETE FROM posts WHERE idPost = :idPost")
    suspend fun deletePost(idPost: Int)

    @Query("SELECT userId FROM posts WHERE idPost = :postId")
    suspend fun getPostOwner(postId: Int): Int

    @Query("""
    SELECT
        posts.idPost AS postId,
        posts.description AS description,
        posts.postImageUri AS imageUri,
        posts.createdAt AS createdAt,
        posts.position AS position,
        posts.userId AS userId,
        users.nickname AS nickname,
        users.name AS name,
        users.surname AS surname,
        users.profileImagePath AS profileImagePath
    FROM posts
    INNER JOIN users ON posts.userId = users.id
    WHERE posts.idPost = :postId
""")
    suspend fun getPostWithUserById(postId: Int): PostWithUser?

}