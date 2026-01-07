package com.example.myicecream.ui.screen.composable



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavBar(val route: String, val icon: ImageVector) {

    object Home : NavBar("home", Icons.Filled.Home)
    object Map : NavBar("map", Icons.Filled.Map)
    object Add : NavBar("add", Icons.Filled.Add)
    object Search : NavBar("search", Icons.Filled.Search)
    object Profile : NavBar("profile", Icons.Filled.Person)
}
