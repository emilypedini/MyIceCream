package com.example.myicecream.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myicecream.data.database.IceCreamDatabase
import com.example.myicecream.data.database.UserEntity
import com.example.myicecream.data.repositories.UserRepository
import com.example.myicecream.ui.composable.ToolBar
import com.example.myicecream.ui.screen.composable.NavBar
import com.example.myicecream.ui.screen.home.HomeScreen
import com.example.myicecream.ui.screen.map.MapScreen
import com.example.myicecream.ui.screen.map.MapViewModel
import com.example.myicecream.ui.screen.profile.ProfileScreen
import com.example.myicecream.ui.screen.profile.ProfileViewModel
import com.example.myicecream.ui.screen.theme.ThemeViewModel

import com.example.myicecream.utils.location.LocationService

@Composable
fun MainScreen(
    rootNavController: NavController,
    themeViewModel: ThemeViewModel,
    loggedUser: UserEntity
) {

    val navController = rememberNavController()

    val context = LocalContext.current
    val db = remember { IceCreamDatabase.getDatabase(context) }
    val userRepository = remember { UserRepository(db.userDAO()) }

    val profileViewModel = remember {
        ProfileViewModel(userRepository, userId = loggedUser.id)
    }

    Scaffold(
        bottomBar = { ToolBar(navController) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = NavBar.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavBar.Home.route) { HomeScreen() }
            composable(NavBar.Map.route) {
                val context = LocalContext.current
                val locationService = remember { LocationService(context) }
                val mapViewModel = remember { MapViewModel(locationService) }

                MapScreen(viewModel = mapViewModel)
            }

            composable(NavBar.Home.route) {
                HomeScreen()
            }

            composable(NavBar.Profile.route) {
                ProfileScreen(
                    navController = rootNavController,
                    viewModel = profileViewModel
                )
            }
        }
    }
}

