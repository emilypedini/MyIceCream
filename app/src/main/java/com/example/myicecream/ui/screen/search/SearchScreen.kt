package com.example.myicecream.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onUserClick: (Int) -> Unit
) {
    val query by viewModel.query.collectAsState()
    val users by viewModel.result.collectAsState()

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            label = {Text("Cerca utenti")},
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        LazyColumn {
            items(users) { user ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onUserClick(user.id) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier.size(48.dp).clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        if(user.profileImagePath != null) {
                            AsyncImage(
                                model = user.profileImagePath,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(0.7f),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = user.nickname,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}