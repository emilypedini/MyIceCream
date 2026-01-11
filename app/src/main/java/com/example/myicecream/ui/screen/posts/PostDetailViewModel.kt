package com.example.myicecream.ui.screen.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.database.PostWithUser
import com.example.myicecream.data.repositories.LikeRepository
import com.example.myicecream.data.repositories.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postRepository: PostRepository,
    private val likeRepository: LikeRepository,
    private val loggedUserId: Int,
    private val postId: Int
): ViewModel() {

    private val _post = MutableStateFlow<PostWithUser?>(null)
    val post = _post.asStateFlow()

    private val _isLiked = MutableStateFlow(false)
    val isLiked = _isLiked.asStateFlow()

    init {
        viewModelScope.launch {
            _post.value = postRepository.getSinglePostWithUserById(postId)
            _isLiked.value = likeRepository.isPostLiked(loggedUserId, postId)
        }
    }

    fun toggleLike() {
        viewModelScope.launch {
            val currentlyLiked = _isLiked.value
            if (currentlyLiked) {
                likeRepository.removeLike(loggedUserId, postId)
            }
            _isLiked.value = !currentlyLiked
        }
    }
}