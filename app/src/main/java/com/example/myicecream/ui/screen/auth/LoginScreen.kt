package com.example.myicecream.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
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
fun LoginScreen(
    onLoginSuccess: (UserEntity) -> Unit, onRegistratiClick: () -> Unit, viewModel: LoginViewModel
) {
    val state by viewModel.loginState
    var showPassword by remember { mutableStateOf(false) }

    AuthHeader {
        Text(
            text = "Accedi o registrati",
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
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it)},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

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

            Button(
                onClick = { viewModel.login()},
                modifier = Modifier.width(200.dp)
            ) { Text("Accedi", fontSize = 18.sp) }

            Spacer(modifier = Modifier.height(24.dp))

            Divider(
                modifier = Modifier.fillMaxWidth().height(2.dp),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRegistratiClick,
                modifier = Modifier.width(200.dp)
            ) {
                Text("Registrati", fontSize = 18.sp)
            }
        }
    }

    LaunchedEffect(state.isUserLogged) {
        if (state.isUserLogged && state.loggedUser != null) {
            onLoginSuccess(state.loggedUser!!)
        }
    }
}
