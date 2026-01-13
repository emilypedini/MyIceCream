package com.example.myicecream.ui.screen.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myicecream.data.database.PostWithUser
import com.example.myicecream.ui.screen.home.HomeViewModel

@Composable
fun PostItem(post: PostWithUser, homeViewModel: HomeViewModel) {

    var isLiked by remember { mutableStateOf(false) }

    LaunchedEffect(post.postId) {
        isLiked = homeViewModel.isPostLiked(post.postId)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {

            val hasProfileImage = !post.profileImagePath.isNullOrBlank()
            if(hasProfileImage) {
                Image(
                    painter = rememberAsyncImagePainter(post.profileImagePath),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier .size(40.dp) .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = post.nickname,
                fontWeight = FontWeight.Bold
            )
        }

        Image(
            painter = rememberAsyncImagePainter(post.imageUri),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(MaterialTheme.shapes.medium)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                homeViewModel.toggleLike(post.postId, isLiked)
                isLiked = !isLiked
            }) {
                Icon(
                    imageVector = if( isLiked)
                        Icons.Filled.Favorite
                    else
                        Icons.Outlined.FavoriteBorder,
                    tint = if (isLiked) Color.Red else Color.Gray,
                    contentDescription = null
                )
            }
        }

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Commento: ") }
                append(post.description) },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )

        post.position?.let { pos ->
            Text(
                text = "Prodotto comprato: $pos",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
    }
}