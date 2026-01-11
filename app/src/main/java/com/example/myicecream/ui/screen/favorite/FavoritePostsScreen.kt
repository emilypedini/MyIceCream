package com.example.myicecream.ui.screen.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun FavoritePostsScreen(
    navController: NavController,
    favoritePostsViewModel: FavoritePostsViewModel
) {
    val favorites by favoritePostsViewModel.favorite.collectAsState()

    LaunchedEffect(favorites.size) {
        favoritePostsViewModel.loadFavorites()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(favorites) { post ->
            Image(
                painter = rememberAsyncImagePainter(post.imageUri),
                contentDescription = null,
                modifier = Modifier.padding(4.dp).fillMaxWidth()
                    .height(180.dp).clickable {
                        navController.navigate("postDetail/${post.postId}")
                    },
                contentScale = ContentScale.Crop
            )
        }
    }
}