package com.example.myicecream.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.database.PostWithUser
import com.example.myicecream.data.repositories.LikeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritePostsViewModel (
    private val likeRepository: LikeRepository,
    private val userId: Int
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<PostWithUser>>(emptyList())
    val favorite = _favorites.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = likeRepository.getFavoritePosts(userId)
        }
    }
}