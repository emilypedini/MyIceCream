package com.example.myicecream.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

@Composable
fun NotificationIcon(
    unreadCount: Int,
    onClick: () -> Unit
) {
    Box {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Notifiche"
            )
        }

        if (unreadCount > 0) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color.Red, CircleShape)
                    .align(Alignment.TopStart)
            )
        }
    }
}
