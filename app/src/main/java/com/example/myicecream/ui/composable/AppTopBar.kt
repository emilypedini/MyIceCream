package com.example.myicecream.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AppTopBar(title: String, innerNavController: NavController) {
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
            onClick = { innerNavController.navigate("notifications") },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                Icons.Default.Send, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary)
        }

        IconButton(
            onClick = {innerNavController.navigate("favorites")},
            modifier = Modifier.align(Alignment.CenterEnd).size(32.dp)
        ) {
            Icon(
                Icons.Default.FavoriteBorder, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}