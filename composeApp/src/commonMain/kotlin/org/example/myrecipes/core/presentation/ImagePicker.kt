package org.example.myrecipes.core.presentation

import androidx.compose.runtime.Composable

expect class ImagePicker {
    @Composable
    fun registerPicker(onImagePicked: (ByteArray) -> Unit)

    fun pickImage()
}