
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

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _surname = MutableStateFlow("")
    val surname = _surname.asStateFlow()

    private val _email = MutableStateFlow("")

    val email = _email.asStateFlow()

    init {
        loadProfileImage()
        loadUserInfo()
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

    private fun loadUserInfo() {
        viewModelScope.launch {
            val user = userRepository.getUserById(userId)
            _name.value = user.name
            _surname.value = user.surname
            _email.value = user.email
        }
    }
}


