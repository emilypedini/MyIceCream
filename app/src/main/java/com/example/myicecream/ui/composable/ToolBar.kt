package com.example.myicecream.ui.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myicecream.ui.composable.NavBar
import androidx.compose.ui.Modifier

@Composable
fun ToolBar(navController: NavController,
            iconSize: Dp = 35.dp) {

    val items = listOf(
        NavBar.Home,
        NavBar.Map,
        NavBar.Add,
        NavBar.Search,
        NavBar.Profile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.route,
                        modifier = Modifier.size(iconSize),
                        tint = if (MaterialTheme.colorScheme.surface == Color.Black) Color.White else Color(0xFF5C4638)
                    )
                }
            )
        }
    }
}
