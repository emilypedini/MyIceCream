package com.example.myicecream.ui.screen.home


import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myicecream.ui.screen.notifications.NotificationsViewModel
import com.example.myicecream.ui.screen.posts.PostItem
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Scaffold

@Composable
fun HomeScreen(
    navController: NavController,
    notificationsViewModel: NotificationsViewModel,
    homeViewModel: HomeViewModel
) {
    val unreadCount by notificationsViewModel.unreadNotificationCount.collectAsState()
    val posts by homeViewModel.posts.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.loadPosts()
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(
                    "Nuvole di Gelato",
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                IconButton(
                    onClick = { navController.navigate("notifications") },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary)
                }

                Icon(
                    Icons.Default.FavoriteBorder, contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.CenterEnd).size(32.dp)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(posts, key = { it.postId }) { post ->
                PostItem(
                    post = post,
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}
