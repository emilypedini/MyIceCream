package com.example.myicecream.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myicecream.ui.screen.theme.ThemeViewModel
import androidx.compose.material3.MaterialTheme

@Composable
fun SettingsScreen(themeViewModel: ThemeViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        Text(
            text = "Impostazioni",
            color = MaterialTheme.colorScheme.onBackground
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
    }
}
