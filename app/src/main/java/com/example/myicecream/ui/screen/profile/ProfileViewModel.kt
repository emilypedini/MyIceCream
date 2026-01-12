
package com.example.myicecream.ui.screen.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.database.PostWithUser
import com.example.myicecream.data.repositories.PostRepository
import com.example.myicecream.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
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

    private val _nickname = MutableStateFlow("")
    val nickname = _nickname.asStateFlow()

    private val _userPosts = MutableStateFlow<List<PostWithUser>>(emptyList())
    val userPosts = _userPosts.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    init {
        loadProfileImage()
        loadUserInfo()
        loadUserPosts()
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

    fun resetProfileImage() {
        viewModelScope.launch {
            userRepository.updateProfileImage(userId, "")
            _profileImageUri.value = null
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            val user = userRepository.getUserById(userId)
            _name.value = user.name
            _surname.value = user.surname
            _email.value = user.email
            _nickname.value = user.nickname
            _isDarkTheme.value = user.screenTheme
        }
    }

    fun updateUserInfo(
        newName: String,
        newSurname: String,
        newNickname: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {

            val nicknameToSave = newNickname.trim()

            val nicknameTaken = userRepository.isNicknameTaken(nicknameToSave, userId)

            if (nicknameTaken) {
                onResult(false, "Questo nickname è già in uso.")
                return@launch
            }

            val success = userRepository.updateUserProfile(
                id = userId,
                name = newName.trim(),
                surname = newSurname.trim(),
                nickname = nicknameToSave,
                profileImagePath = _profileImageUri.value?.toString() ?: "",
                screenTheme = _isDarkTheme.value
            )



            _name.value = newName
            _surname.value = newSurname
            _nickname.value = nicknameToSave

            onResult(true, "Profilo aggiornato")
        }
    }

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        onResult:(Boolean, String) -> Unit
    ){
        viewModelScope.launch{
            val user = userRepository.getUserById(userId)
            if(user.password != currentPassword) {
                onResult(false, "La password attuale non è corretta.")
                return@launch
            }
            userRepository.updatePassword(userId, newPassword)
            onResult(true, "Password aggiornata con successo.")
        }
    }

    fun loadUserPosts(){
        viewModelScope.launch {
            _userPosts.value = postRepository.getPostsByUser(userId)
        }
    }

    fun deletePost(postId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = postRepository.deletePost(postId)
            if(success) {
                loadUserPosts()
            }
            onResult(success)
        }
    }

    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }

}


