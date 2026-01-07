package com.example.myicecream

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myicecream.ui.theme.MyIceCreamTheme
import com.example.myicecream.ui.MainNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyIceCreamTheme {
                MainNavigation()
            }
        }
    }
}
@Composable
fun WelcomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Benvenuti nella nostra gelateria 🍦")
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    MyIceCreamTheme {
        WelcomeScreen()
    }
}
