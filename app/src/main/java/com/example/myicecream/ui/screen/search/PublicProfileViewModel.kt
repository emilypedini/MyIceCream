package com.example.myicecream.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.database.PostWithUser
import com.example.myicecream.data.database.UserEntity
import com.example.myicecream.data.repositories.PostRepository
import com.example.myicecream.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PublicProfileViewModel(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val userId: Int
): ViewModel() {

    val user = MutableStateFlow<UserEntity?>(null)
    val posts = MutableStateFlow<List<PostWithUser>>(emptyList())

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            user.value = userRepository.getUserById(userId)
            posts.value = postRepository.getPostsByUser(userId)
        }
    }
}