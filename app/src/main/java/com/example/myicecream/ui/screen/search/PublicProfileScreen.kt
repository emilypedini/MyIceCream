package com.example.myicecream.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun PublicProfileScreen (
    viewModel: PublicProfileViewModel,
    onPostClick: (Int) -> Unit
) {
    val user by viewModel.user.collectAsState()
    val posts by viewModel.posts.collectAsState()

    user ?: return

    Column {
        Text(
            text = user!!.nickname,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(posts) { post ->
                Image(
                    painter = rememberAsyncImagePainter(post.imageUri),
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp).height(180.dp)
                        .clickable {
                            onPostClick(post.postId)
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}