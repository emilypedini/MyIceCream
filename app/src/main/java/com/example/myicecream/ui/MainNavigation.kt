package com.example.myicecream.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myicecream.data.database.IceCreamDatabase
import com.example.myicecream.data.repositories.AuthRepository
import com.example.myicecream.ui.screen.auth.LoginScreen
import com.example.myicecream.ui.screen.auth.LoginViewModel
import com.example.myicecream.ui.screen.singup.RegistrazioneScreen
import com.example.myicecream.ui.screen.init.Avvio
import com.example.myicecream.ui.screen.singup.SignUpViewModel
import com.example.myicecream.ui.screen.main.MainScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val db = remember {
        IceCreamDatabase.getDatabase(context)
    }
    val authRepository = remember {
        AuthRepository(db.userDAO())
    }

    NavHost(navController = navController, startDestination = "inizio") {

        composable("inizio") {
            Avvio(navController)
        }

        composable("login") {
            val loginViewModel = remember {
                LoginViewModel(authRepository)
            }

            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegistratiClick = {
                    navController.navigate("registrazione")
                },
                viewModel = loginViewModel
            )
        }

        composable("registrazione") {
            val signUpViewModel = remember {
                SignUpViewModel(authRepository)
            }
            RegistrazioneScreen(onSignUpSuccess = {
                navController.navigate("main") {
                    popUpTo("registrazione") { inclusive = true }
                }
            },
            viewModel = signUpViewModel)
        }

        composable("main") {
            MainScreen()
        }
    }
}