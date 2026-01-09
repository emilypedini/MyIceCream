package com.example.myicecream.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myicecream.ui.screen.theme.ThemeViewModel
import com.example.utils.camera.rememberCameraLauncher
import com.example.utils.camera.rememberGalleryLauncher

@Composable
fun SettingsScreen(
    themeViewModel: ThemeViewModel,
    profileViewModel: ProfileViewModel
) {
    val imageUri by profileViewModel.profileImageUri.collectAsState()
    val name by profileViewModel.name.collectAsState()
    val surname by profileViewModel.surname.collectAsState()

    var newName by remember { mutableStateOf(name) }
    var newSurname by remember { mutableStateOf(surname) }

    val cameraLauncher = rememberCameraLauncher { uri ->
        profileViewModel.onImageCaptured(uri)
    }

    val galleryLauncher = rememberGalleryLauncher { uri ->
        profileViewModel.onImageCaptured(uri)
    }

    var menuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        Text(
            text = "Modifica profilo",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .clickable { menuExpanded = true },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Foto profilo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Scatta foto") },
                    onClick = {
                        menuExpanded = false
                        cameraLauncher.captureImage()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Scegli dalla galleria") },
                    onClick = {
                        menuExpanded = false
                        galleryLauncher.pickImage()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { profileViewModel.resetProfileImage() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Rimuovi foto profilo", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = newName,
            onValueChange = { newName = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = newSurname,
            onValueChange = { newSurname = it },
            label = { Text("Cognome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Tema scuro",
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = themeViewModel.isDarkTheme.value,
                onCheckedChange = { themeViewModel.setDarkTheme(it) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                profileViewModel.updateUserInfo(newName, newSurname)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salva modifiche")
        }
    }
}
