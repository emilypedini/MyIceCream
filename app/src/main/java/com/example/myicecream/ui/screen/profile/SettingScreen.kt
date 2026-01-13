package com.example.myicecream.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myicecream.ui.screen.theme.ThemeViewModel
import com.example.utils.camera.rememberCameraLauncher
import com.example.utils.camera.rememberGalleryLauncher

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.utils.camera.saveImageToStorage

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

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var newName by remember { mutableStateOf(name) }
    var newSurname by remember { mutableStateOf(surname) }
    var newNickname by remember { mutableStateOf(nickname) }

    LaunchedEffect(name, surname, nickname) {
        newName = name
        newSurname = surname
        newNickname = nickname
    }


    var menuExpanded by remember { mutableStateOf(false) }
    var popUpPhoto by remember { mutableStateOf(false) }
    var popUpSave by remember { mutableStateOf(false) }
    var popUpError by remember { mutableStateOf(false) }
    var popUpPwd by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPwd by remember { mutableStateOf(false) }
    var showReconfirmPwd by remember { mutableStateOf(false) }

    val cameraLauncher = rememberCameraLauncher { uri ->
        val savedUri = saveImageToStorage(uri, context.contentResolver)
        profileViewModel.onImageCaptured(savedUri)
    }

    val galleryLauncher = rememberGalleryLauncher { uri ->
        val savedUri = saveImageToStorage(uri, context.contentResolver)
        profileViewModel.onImageCaptured(savedUri)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = "Modifica profilo",
            color = MaterialTheme.colorScheme.primary,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {

            item {
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
                            imageVector = Icons.Default.Person,
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
            }

            item { Spacer(Modifier.height(12.dp)) }

            item {
                Button(
                    onClick = { popUpPhoto = true },
                    modifier = Modifier.width(200.dp)
                ) {
                    Text("Rimuovi foto profilo")
                }
            }

            item { Spacer(Modifier.height(24.dp)) }

            item {
                TextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Down)
                    }

                )
            }

            item { Spacer(Modifier.height(12.dp)) }

            item {
                TextField(
                    value = newSurname,
                    onValueChange = { newSurname = it },
                    label = { Text("Cognome") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            }

            item { Spacer(Modifier.height(12.dp)) }

            item {
                TextField(
                    value = newNickname,
                    onValueChange = { newNickname = it },
                    label = { Text("Nickname") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
            }

            item { Spacer(Modifier.height(24.dp)) }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text="Tema scuro", color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = profileViewModel.isDarkTheme.collectAsState().value,
                        onCheckedChange = { isChecked ->
                            profileViewModel.setDarkTheme(isChecked)
                            themeViewModel.setDarkTheme(isChecked)
                        }
                    )
                }
            }

            item { Spacer(Modifier.height(24.dp)) }

            item {
                Button(
                    onClick = {
                        if (newName.isBlank() || newSurname.isBlank() || newNickname.isBlank()) {
                            popUpError = true
                        } else {
                            popUpSave = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Salva modifiche")
                }
            }

            item { Spacer(Modifier.height(12.dp)) }

            item {
                Button(
                    onClick = { popUpPwd = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Modifica password")
                }
            }
        }
    }

    if (popUpPhoto) {
        AlertDialog(
            onDismissRequest = { popUpPhoto = false },
            title = { Text("Rimuovere la foto?") },
            text = { Text("Sei sicura di voler eliminare la foto profilo?") },
            confirmButton = {
                TextButton(onClick = {
                    profileViewModel.resetProfileImage()
                    popUpPhoto = false
                }) {
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

    if (popUpError) {
        AlertDialog(
            onDismissRequest = { popUpError = false },
            title = { Text("Campi non validi") },
            text = { Text("Nome, cognome e nickname non possono essere vuoti.") },
            confirmButton = {
                TextButton(onClick = { popUpError = false }) {
                    Text("OK")
                }
            }
        )
    }

    if (popUpSave) {
        AlertDialog(
            onDismissRequest = { popUpSave = false },
            title = { Text("Salvare le modifiche?") },
            confirmButton = {
                TextButton(onClick = {
                    profileViewModel.updateUserInfo(
                        newName,
                        newSurname,
                        newNickname
                    ) { success, message ->
                        if (!success) {
                            errorMessage = message
                        } else {
                            popUpSave = false
                            navController.popBackStack()
                        }
                    }
                }) {
                    Text("Sì")
                }
            },
            dismissButton = {
                TextButton(onClick = { popUpSave = false }) {
                    Text("Annulla")
                }
            }
        )
    }

    if (popUpPwd) {
        AlertDialog(
            onDismissRequest = {
                popUpPwd = false
                passwordError = null
            },
            title = { Text("Modifica password") },
            text = {
                Column {
                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = { Text("Password attuale") },
                        singleLine = true,
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Nuova password") },
                        singleLine = true,
                        visualTransformation = if (showConfirmPwd) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showConfirmPwd = !showConfirmPwd }) {
                                Icon(
                                    imageVector = if (showConfirmPwd) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = confirmNewPassword,
                        onValueChange = { confirmNewPassword = it },
                        label = { Text("Conferma nuova password") },
                        singleLine = true,
                        visualTransformation = if (showReconfirmPwd) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showReconfirmPwd = !showReconfirmPwd }) {
                                Icon(
                                    imageVector = if (showReconfirmPwd) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        })
                    )

                    if (passwordError != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = passwordError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    when {
                        newPassword.isBlank() || confirmNewPassword.isBlank() -> {
                            passwordError = "Compila tutti i campi"
                        }
                        newPassword != confirmNewPassword -> {
                            passwordError = "Le nuove password non coincidono"
                        }
                        else -> {
                            profileViewModel.changePassword(
                                currentPassword,
                                newPassword
                            ) { success, message ->
                                passwordError = if (!success) message else null
                                if (success) {
                                    popUpPwd = false
                                    currentPassword = ""
                                    newPassword = ""
                                    confirmNewPassword = ""
                                }
                            }
                        }
                    }
                }) {
                    Text("Conferma")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    popUpPwd = false
                    passwordError = null
                }) {
                    Text("Annulla")
                }
            }
        )
    }
}
