
package com.example.myicecream.ui.screen.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val userId: Int
) : ViewModel() {

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri = _profileImageUri.asStateFlow()

    init {
        loadProfileImage()
    }

    private fun loadProfileImage() {
        viewModelScope.launch {
            val path = userRepository.getUserProfileImage(userId)
            _profileImageUri.value = if (path.isNullOrEmpty()) null else Uri.parse(path)
        }
    }

    fun onImageCaptured(uri: Uri) {
        _profileImageUri.value = uri
        saveProfileImage(uri)
    }

    private fun saveProfileImage(uri: Uri) {
        viewModelScope.launch {
            userRepository.updateProfileImage(userId, uri.toString())
        }
    }
}


