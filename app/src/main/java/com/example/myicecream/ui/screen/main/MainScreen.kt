package com.example.myicecream.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myicecream.ui.composable.ToolBar
import com.example.myicecream.ui.screen.composable.NavBar
import com.example.myicecream.ui.screen.home.HomeScreen

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { ToolBar(navController) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = NavBar.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavBar.Home.route) { HomeScreen() }
            //composable(ScreenNav.Map.route) { MapScreen() }
            //composable(ScreenNav.Add.route) { AddScreen() }
            //composable(ScreenNav.Search.route) { SearchScreen() }
            //composable(ScreenNav.Profile.route) { ProfileScreen() }
        }
    }
}
