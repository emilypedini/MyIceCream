package com.example.myicecream.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myicecream.ui.screen.auth.LoginScreen
import com.example.myicecream.ui.screen.singup.RegistrazioneScreen
import com.example.myicecream.ui.screen.init.Avvio

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "inizio") {
        composable("inizio") { Avvio(navController) }
        composable("login") { LoginScreen(onRegistratiClick = { navController.navigate("registrazione") }) }
        composable("registrazione") { RegistrazioneScreen() }
    }

}