package com.example.myicecream.ui.screen.posts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myicecream.ui.screen.posts.PostDetailContent


@Composable
fun PostDetailScreen(
    postDetailViewModel: PostDetailViewModel
) {
    val post by postDetailViewModel.post.collectAsState()
    val isLiked by postDetailViewModel.isLiked.collectAsState()

    post?.let { postWithUser ->
        PostDetailContent(
            post = postWithUser,
            isLiked = isLiked,
            onToggleLike = { postDetailViewModel.toggleLike() }
        )
    }

}