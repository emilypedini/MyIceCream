package com.example.myicecream.ui.singup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myicecream.data.database.UserEntity
import com.example.myicecream.data.repositories.AuthRepository
import com.example.myicecream.ui.auth.LoginState
import kotlinx.coroutines.launch

data class SignUpState(
    val name: String = "",
    val surname: String = "",
    val phone: String? = "",
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

    fun onPhoneChange(phone: String?) {
        _singupState.value = _singupState.value.copy(phone = phone)
    }

    fun onEmailChange(email: String) {
        _singupState.value = _singupState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _singupState.value = _singupState.value.copy(password = password)
    }

    fun signup() {
        val state = _singupState.value

        if(state.name.isBlank() || state.surname.isBlank() ||
            state.email.isBlank() || state.password.isBlank()) {
            _singupState.value = state.copy(

                messError = "Compilare tutti i campi obbligatori"
            )
            return
        }

        if(!state.email.contains("@")){
            _singupState.value = state.copy(
                messError = "Email inserita non valida"
            )
            return
        }

        if(state.password.length < 8){
            _singupState.value = state.copy(
                messError = "Password inserita non valida (min 8 caratteri)"
            )
            return
        }

        viewModelScope.launch {
            _singupState.value = state.copy(isLoading = true)

            val singUpSuccess = authRepository.signUp(
                UserEntity(
                    name = state.name,
                    surname = state.surname,
                    email = state.email,
                    password = state.password,
                    phone = state.phone,
                    googleId = null,
                    profileImagePath = null
                )
            )

            _singupState.value = if (singUpSuccess) {
                state.copy(
                    isLoading = false,
                    isRegistered = true,
                    messError = null
                )
            } else {
                state.copy(
                    isLoading = false,
                    messError = "Email già registrata"
                )
            }
        }
    }
}