package com.example.myicecream.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myicecream.data.database.IceCreamDatabase
import com.example.myicecream.data.database.UserEntity
import com.example.myicecream.data.repositories.AuthRepository
import com.example.myicecream.data.repositories.NotificationRepository
import com.example.myicecream.data.repositories.UserRepository
import com.example.myicecream.ui.screen.auth.LoginScreen
import com.example.myicecream.ui.screen.auth.LoginViewModel
import com.example.myicecream.ui.screen.singup.RegistrazioneScreen
import com.example.myicecream.ui.screen.singup.SignUpViewModel
import com.example.myicecream.ui.screen.init.Avvio
import com.example.myicecream.ui.screen.main.MainScreen
import com.example.myicecream.ui.screen.notifications.NotificationScreen
import com.example.myicecream.ui.screen.notifications.NotificationsViewModel
import com.example.myicecream.ui.screen.profile.ProfileViewModel
import com.example.myicecream.ui.screen.profile.SettingsScreen
import com.example.myicecream.ui.screen.theme.ThemeViewModel

@Composable
fun MainNavigation(themeViewModel: ThemeViewModel) {

    val navController = rememberNavController() // NavController principale
    val context = LocalContext.current

    val db = remember { IceCreamDatabase.getDatabase(context) }
    val authRepository = remember { AuthRepository(db.userDAO(),
        notificationRepository = NotificationRepository(db.notificationDAO())
    )}

    var loggedUser by remember { mutableStateOf<UserEntity?>(null) }

    NavHost(navController = navController, startDestination = "inizio") {

        composable("inizio") {
            Avvio(navController)
        }

        composable("login") {
            val loginViewModel = remember { LoginViewModel(authRepository) }
            LoginScreen(
                onLoginSuccess = { user ->
                    loggedUser = user
                    navController.navigate("main") { popUpTo("login") { inclusive = true } }
                },
                onRegistratiClick = { navController.navigate("registrazione") },
                viewModel = loginViewModel
            )
        }

        composable("registrazione") {
            val signUpViewModel = remember { SignUpViewModel(authRepository) }
            RegistrazioneScreen(
                onSignUpSuccess = { user ->
                    loggedUser = user
                    navController.navigate("main") { popUpTo("registrazione") { inclusive = true } }
                },
                viewModel = signUpViewModel
            )
        }

        composable("main") {
            loggedUser?.let { user ->
                MainScreen(
                    rootNavController = navController,
                    themeViewModel = themeViewModel,
                    loggedUser = user
                )
            }
        }


        composable("settings") {
            loggedUser?.let { user ->
                val profileViewModel = remember {
                    ProfileViewModel(
                        userRepository = UserRepository(db.userDAO()),
                        userId = user.id
                    )
                }

                SettingsScreen(
                    themeViewModel = themeViewModel,
                    profileViewModel = profileViewModel,
                    navController = navController
                )
            }
        }

        composable("notifications") {
            loggedUser?.let { user ->
                val notificationsViewModel = remember {
                    NotificationsViewModel(
                        notificationRepository = NotificationRepository(db.notificationDAO()),
                        userId = user.id
                    )
                }
                NotificationScreen(viewModel = notificationsViewModel)
            }
        }
    }
}
