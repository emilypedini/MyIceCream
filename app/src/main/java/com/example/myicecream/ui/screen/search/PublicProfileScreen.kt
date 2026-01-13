package com.example.myicecream.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter

@Composable
fun PublicProfileScreen (
    viewModel: PublicProfileViewModel,
    onPostClick: (Int) -> Unit
) {
    val user by viewModel.user.collectAsState()
    val posts by viewModel.posts.collectAsState()

    user ?: return

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = user!!.nickname,
                modifier = Modifier.align(Alignment.Center),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp, start = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {

                if (user!!.profileImagePath != null) {
                    AsyncImage(
                        model = user!!.profileImagePath,
                        contentDescription = "Foto profilo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Aggiungi foto",
                        modifier = Modifier.size(135.dp),
                        tint = MaterialTheme.colorScheme.primary

                    )
                }
            }

            Spacer(modifier = Modifier.width(24.dp))

            Column {
                Text(
                    text = user!!.nickname,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${user!!.name} ${user!!.surname}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text(
            text = "Post pubblicati",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.primary
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