package com.example.myicecream.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myicecream.ui.screen.posts.PostItem
import androidx.compose.foundation.lazy.items

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {

    val posts by homeViewModel.posts.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.loadPosts()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
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
