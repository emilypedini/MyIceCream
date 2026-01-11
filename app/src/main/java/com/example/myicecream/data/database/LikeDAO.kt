package com.example.myicecream.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LikeDAO {

    @Insert
    suspend fun addLike(like: LikeEntity)

    @Query("DELETE FROM likes WHERE userId = :userId AND postId = :postId")
    suspend fun removeLike(userId: Int, postId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM likes WHERE userId = :userId AND postId = :postId)")
    suspend fun isPostLiked(userId: Int, postId: Int): Boolean

    @Query("""
    SELECT
        p.idPost AS postId,
        p.description AS description,
        p.postImageUri AS imageUri,
        p.createdAt AS createdAt,
        u.id AS userId,
        u.nickname AS nickname,
        u.name AS name,
        u.surname AS surname,
        u.profileImagePath AS profileImagePath
    FROM posts p
    INNER JOIN likes l ON p.idPost = l.postId
    INNER JOIN users u ON p.userId = u.id
    WHERE l.userId = :userId
""")
    suspend fun getFavoritePosts(userId: Int): List<PostWithUser>

}