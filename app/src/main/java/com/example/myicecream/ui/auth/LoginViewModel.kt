package com.example.myicecream.ui.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.database.UserEntity
import com.example.myicecream.data.repositories.AuthRepository
import kotlinx.coroutines.launch

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val messageError: String? = null,
    val loggedUser: UserEntity? = null //sapere se il login è OK
){}

class LoginViewModel( private val authRepository: AuthRepository) : ViewModel(){

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    fun onEmailChange(email: String) {
        _loginState.value = _loginState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _loginState.value = _loginState.value.copy(password = password)
    }

    fun login() {
        val email = _loginState.value.email
        val password = _loginState.value.password

        if(email.isBlank() || password.isBlank()) {
            _loginState.value = _loginState.value.copy(
                messageError = "Compilare tutti i campi"
            )
            return
        }

        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoading = true)

            val user = authRepository.login(email, password)

            _loginState.value = if (user !=  null) {
                _loginState.value.copy(
                    isLoading = false,
                    loggedUser = user,
                    messageError = null
                )
            } else {
                _loginState.value.copy(
                    isLoading = false,
                    messageError = "Credenziali errate"
                )
            }
        }
    }
}