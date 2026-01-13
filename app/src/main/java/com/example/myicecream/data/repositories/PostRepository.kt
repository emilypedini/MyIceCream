package com.example.myicecream.data.repositories

import com.example.myicecream.data.database.PostDAO
import com.example.myicecream.data.database.PostEntity
import com.example.myicecream.data.database.PostWithUser

class PostRepository(private val postDAO: PostDAO) {

    suspend fun createNewPost(post: PostEntity): Boolean {
        return try {
            postDAO.insertPost(post)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getAllPosts(): List<PostWithUser> {
        return postDAO.getAllPosts()
    }

    suspend fun getPostsByUser(userId: Int): List<PostWithUser> {
        return postDAO.getPostByUser(userId)
    }

    suspend fun deletePost(idPost: Int): Boolean {
        return try {
            postDAO.deletePost(idPost)
            true
        } catch (e: Exception){
            false
        }
    }

    suspend fun getSinglePostWithUserById(postId: Int): PostWithUser? {
        return postDAO.getPostWithUserById(postId)
    }
}