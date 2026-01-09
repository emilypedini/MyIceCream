package com.example.myicecream.ui.screen.singup

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.database.UserEntity
import com.example.myicecream.data.repositories.AuthRepository
import kotlinx.coroutines.launch

data class SignUpState(
    val name: String = "",
    val surname: String = "",
    val nickname: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val messError: String? = null,
    val isRegistered: Boolean = false
)

class SignUpViewModel( private val authRepository: AuthRepository) : ViewModel() {

    private val _singupState = mutableStateOf(SignUpState())
    val singupState: State<SignUpState> = _singupState

    fun onNameChange(name: String) {
        _singupState.value = _singupState.value.copy(name = name)
    }

    fun onSurnameChange(surname: String) {
        _singupState.value = _singupState.value.copy(surname = surname)
    }

    fun onPhoneChange(phone: String) {
        _singupState.value = _singupState.value.copy(phone = phone)
    }

    fun onEmailChange(email: String) {
        _singupState.value = _singupState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _singupState.value = _singupState.value.copy(password = password)
    }

    fun onNicknameChange(nickname: String) {
        _singupState.value = _singupState.value.copy(nickname = nickname)
    }

    fun signupAndLogin(onResult: (UserEntity?) -> Unit) {
        val state = _singupState.value

        if(state.name.isBlank() || state.surname.isBlank() || state.email.isBlank() || state.password.isBlank() || state.nickname.isBlank()) {
            _singupState.value = state.copy(messError = "Compilare tutti i campi obbligatori")
            onResult(null)
            return
        }

        if(!state.email.contains("@")){
            _singupState.value = state.copy(messError = "Email inserita non valida")
            onResult(null)
            return
        }

        if(state.password.length < 8){
            _singupState.value = state.copy(messError = "Password inserita non valida (min 8 caratteri)")
            onResult(null)
            return
        }

        viewModelScope.launch {
            _singupState.value = state.copy(isLoading = true)

            try {
                val signUpSuccess = authRepository.signUp(
                    UserEntity(
                        name = state.name,
                        surname = state.surname,
                        nickname = state.nickname,
                        email = state.email,
                        password = state.password,
                        phone = state.phone,
                        googleId = null,
                        profileImagePath = null
                    )
                )

                if (signUpSuccess) {
                    val user = authRepository.login(state.email, state.password)
                    _singupState.value = state.copy(
                        isLoading = false,
                        isRegistered = true,
                        messError = null
                    )
                    onResult(user)
                }

            } catch (e: IllegalArgumentException) {

                val errorMessage = when (e.message) {
                    "EMAIL_EXISTS" -> "Email già registrata"
                    "NICKNAME_EXISTS" -> "Nickname già in uso"
                    else -> "Errore durante la registrazione"
                }

                _singupState.value = state.copy(
                    isLoading = false,
                    messError = errorMessage
                )
                onResult(null)
            }
        }

    }

}
