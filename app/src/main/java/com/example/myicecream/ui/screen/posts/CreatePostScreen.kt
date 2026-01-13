package com.example.myicecream.ui.screen.posts

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.utils.camera.rememberCameraLauncher
import com.example.utils.camera.rememberGalleryLauncher
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization

@Composable
fun CreatePostScreen(
    navController: NavController,
    postViewModel: PostViewModel
) {
    val imageUri by postViewModel.imageUri.collectAsState()
    val description by postViewModel.description.collectAsState()
    val focusManager = LocalFocusManager.current
    val cameraLauncher = rememberCameraLauncher { uri ->
        postViewModel.onImageSelect(uri.toString())
    }

    val galleryLauncher = rememberGalleryLauncher { uri ->
        postViewModel.onImageSelect(uri.toString())
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .imePadding()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {

            item {
                Text(
                text = "Crea un nuovo post",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
                )
            }


            item{
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(Uri.parse(imageUri)),
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(12.dp)).background(
                            Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Nessuna immagine selezionata")
                    }
                }
            }


            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item{
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { cameraLauncher.captureImage()},
                        modifier = Modifier.weight(1f)) {
                        Text("Scatta Foto")
                    }

                    Button(
                        onClick = {galleryLauncher.pickImage()},
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Galleria")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item{
                OutlinedTextField(
                    value = description,
                    onValueChange = {postViewModel.onDescriptionChange(it)},
                    label = { Text("Descrizione")},
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
            }

            item{
                Spacer(modifier = Modifier.height(24.dp))
            }

            item{

                Button(
                    onClick = {
                        postViewModel.createPost(
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Post pubblicato!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onError = {
                                Toast.makeText(
                                    context,
                                    "Errore nella pubblicazione",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = imageUri != null && description.isNotBlank()
                ) {
                    Text("Pubblica")
                }
            }

        }


















    }
}