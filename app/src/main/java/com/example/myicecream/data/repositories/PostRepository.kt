package com.example.myicecream.data.repositories

import com.example.myicecream.data.database.NotificationDAO
import com.example.myicecream.data.database.NotificationEntity
import com.example.myicecream.data.database.PostDAO
import com.example.myicecream.data.database.PostEntity
import com.example.myicecream.data.database.PostWithUser

class PostRepository(private val postDAO: PostDAO, private val notificationDAO: NotificationDAO) {

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

    suspend fun getPostsByUser(userId: Int): List<PostEntity> {
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

    suspend fun createOfficialPost(post: PostEntity){
        postDAO.insertPost(post)

        notificationDAO.insertNotification(NotificationEntity(
            userId = null,
            title = "Nuovo Post di Nuvole di Gelato",
            message = "Nuvole di Gelato ha pubblicato un nuovo post, corri a vederlo!!"
        ))
    }

    suspend fun getSinglePostWithUserById(postId: Int): PostWithUser? {
        return postDAO.getPostWithUserById(postId)
    }
}