package com.example.myicecream.ui.screen.singup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myicecream.data.database.UserEntity
import com.example.myicecream.ui.screen.init.AuthHeader

@Composable
fun RegistrazioneScreen(onSignUpSuccess: (UserEntity) -> Unit, viewModel: SignUpViewModel) {
    val state by viewModel.singupState

    var showPassword by remember { mutableStateOf(false) }

    AuthHeader {
        Text(
            text = "Registrati",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChange(it)},
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth(),
                        singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = state.surname,
                onValueChange = { viewModel.onSurnameChange(it)},
                label = { Text("Cognome") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = state.nickname,
                onValueChange = { viewModel.onNicknameChange(it)},
                label = { Text("Nickname") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = state.phone,
                onValueChange = { viewModel.onPhoneChange(it)},
                label = { Text("Telefono") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it)},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it)},
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(imageVector = icon, contentDescription = if (showPassword) "Nascondi password" else "Mostra password")
                    }
                }
            )


            Spacer(modifier = Modifier.height(24.dp))

            if (state.messError != null) {
                Text(
                    text = state.messError!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 12.dp) )
            }
            Button(
                onClick = {
                    viewModel.signupAndLogin { user ->
                        if (user != null) {
                            onSignUpSuccess(user)
                        }
                    }
                },

                enabled = !state.isLoading,
                modifier = Modifier.width(200.dp)
            ) { Text("Registrati", fontSize = 18.sp) }
        }
    }

}

