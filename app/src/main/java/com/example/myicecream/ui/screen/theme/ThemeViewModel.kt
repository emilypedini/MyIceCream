package com.example.myicecream.ui.screen.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {
    val isDarkTheme = mutableStateOf(false)

    fun setDarkTheme(dark: Boolean) {
        isDarkTheme.value = dark
    }
}
