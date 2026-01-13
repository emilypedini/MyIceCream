package com.example.myicecream.ui.screen.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.database.PostEntity
import com.example.myicecream.data.repositories.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val postRepository: PostRepository,
    private val userId: Int
): ViewModel() {

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri = _imageUri.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _position = MutableStateFlow<String?>(null)
    val position = _position.asStateFlow()

    fun onImageSelect(uri: String) {
        _imageUri.value = uri
    }

    fun onDescriptionChange(text: String) {
        _description.value = text
    }

    fun onPositionSelected(shopName: String?) {
        _position.value = shopName
    }

    fun createPost(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val image = imageUri.value ?: return
        val descr = description.value
        val pos = position.value

        viewModelScope.launch {
            val success = postRepository.createNewPost(
                PostEntity(
                    userId = userId,
                    postImageUri = image,
                    description = descr,
                    position = pos
                )
            )
            if(success) {
                _imageUri.value = null
                _description.value = ""
                _position.value = null
                onSuccess()
            } else {
                onError()
            }
        }
    }
}