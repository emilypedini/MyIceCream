package com.example.utils.camera

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*

interface GalleryLauncher {
    fun pickImage()
}

@Composable
fun rememberGalleryLauncher(
    onImageSelected: (Uri) -> Unit
): GalleryLauncher {

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onImageSelected(it) }
        }

    return remember(galleryLauncher) {
        object : GalleryLauncher {
            override fun pickImage() {
                galleryLauncher.launch("image/*")
            }
        }
    }
}
