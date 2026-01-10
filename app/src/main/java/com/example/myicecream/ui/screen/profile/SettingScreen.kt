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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myicecream.ui.screen.theme.ThemeViewModel
import com.example.utils.camera.rememberCameraLauncher
import com.example.utils.camera.rememberGalleryLauncher

@Composable
fun SettingsScreen(
    themeViewModel: ThemeViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController
) {
    val imageUri by profileViewModel.profileImageUri.collectAsState()
    val name by profileViewModel.name.collectAsState()
    val surname by profileViewModel.surname.collectAsState()
    val nickname by profileViewModel.nickname.collectAsState()

    var newName by remember { mutableStateOf(name) }
    var newSurname by remember { mutableStateOf(surname) }
    var newNickname by remember { mutableStateOf(nickname) }

    val cameraLauncher = rememberCameraLauncher { uri ->
        profileViewModel.onImageCaptured(uri)
    }

    LaunchedEffect(name, surname, nickname) {
        newName= name
        newSurname = surname
        newNickname = nickname
    }

    val galleryLauncher = rememberGalleryLauncher { uri ->
        profileViewModel.onImageCaptured(uri)
    }

    var popUpPwd by remember { mutableStateOf(false)}
    var popUpError by remember { mutableStateOf(false)}
    var menuExpanded by remember { mutableStateOf(false) }
    var popUpPhoto by remember { mutableStateOf(false) }
    var popUpSave by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Modifica profilo",
            color = MaterialTheme.colorScheme.primary,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 30.sp,
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
            onClick = { popUpPhoto = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.width(200.dp)
        ) {
            Text("Rimuovi foto profilo",
                color = MaterialTheme.colorScheme.background
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = newName,
            onValueChange = { newName = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = newSurname,
            onValueChange = { newSurname = it },
            label = { Text("Cognome") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = newNickname,
            onValueChange = { newNickname = it },
            label = { Text("Nickname") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
                if(newName.isBlank() || newSurname.isBlank() || newNickname.isBlank()){
                    popUpError = true
                } else{
                    popUpSave = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salva modifiche")
        }
        if (popUpPhoto) {
            AlertDialog(
                onDismissRequest = { popUpPhoto = false },
                title = { Text("Rimuovere la foto?") },
                text = { Text("Sei sicura di voler eliminare la foto profilo?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            profileViewModel.resetProfileImage()
                            popUpPhoto = false
                        }
                ) {
                        Text("Sì", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { popUpPhoto = false }) {
                        Text("Annulla")
                    }
                }
            )
        }
        if(popUpSave) {
            AlertDialog(
                onDismissRequest = { popUpSave = false },
                title = { Text("Salvare le modifiche?") },
                text = { Text("Vuoi salvare le modifiche apportate al profilo?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            profileViewModel.updateUserInfo(newName,newSurname,newNickname)
                            popUpSave = false
                            navController.popBackStack()
                        }
                    ) {
                        Text("Sì", color = MaterialTheme.colorScheme.primary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = {popUpSave= false
                        navController.popBackStack()}
                    ) {
                        Text("Annulla")
                    }
                }

            )
        }


        if(popUpError){
            AlertDialog(
                onDismissRequest = { popUpError = false },
                title = { Text("Campi non validi") },
                text = { Text("Nome e cognome non possono essere vuoti.") },
                confirmButton = {
                    TextButton(onClick = { popUpError = false }) {
                        Text("OK")
                    }
                }
            )
        }

        Button(
            onClick = {popUpPwd = true},
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Modifica password")
        }

        if (popUpPwd) {
            var currentPwd by remember { mutableStateOf("") }
            var newPwd by remember { mutableStateOf("") }
            var confirmPwd by remember { mutableStateOf("") }
            var errorMessage by remember { mutableStateOf<String?>(null) }

            AlertDialog(
                onDismissRequest = { popUpPwd = false },
                title = { Text("Modifica password") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = currentPwd,
                            onValueChange = { currentPwd = it },
                            label = { Text("Password attuale") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = newPwd,
                            onValueChange = { newPwd = it },
                            label = { Text("Nuova password") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = confirmPwd,
                            onValueChange = { confirmPwd = it },
                            label = { Text("Conferma nuova password") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (errorMessage != null) {
                            Spacer(Modifier.height(12.dp))
                            Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (newPwd != confirmPwd) {
                                errorMessage = "Le nuove password non combaciano."
                                return@TextButton
                            }

                            if (newPwd.isBlank()) {
                                errorMessage = "La nuova password non può essere vuota."
                                return@TextButton
                            }

                            profileViewModel.changePassword(
                                currentPassword = currentPwd,
                                newPassword = newPwd
                            ) { success, message ->
                                if (!success) {
                                    errorMessage = message
                                } else {
                                    popUpPwd = false
                                }
                            }
                        }
                    ) {
                        Text("Salva")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { popUpPwd = false }) {
                        Text("Annulla")
                    }
                }
            )
        }

    }

}
