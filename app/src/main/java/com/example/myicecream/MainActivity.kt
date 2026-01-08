package com.example.myicecream

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.myicecream.ui.MainNavigation
import com.example.myicecream.ui.theme.MyIceCreamTheme
import com.example.myicecream.ui.screen.theme.ThemeViewModel

class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyIceCreamTheme(darkTheme = themeViewModel.isDarkTheme.value) {
                MainNavigation(themeViewModel)
            }
        }
    }
}
