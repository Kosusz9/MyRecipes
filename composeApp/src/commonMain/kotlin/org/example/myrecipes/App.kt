package org.example.myrecipes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import org.example.myrecipes.core.presentation.AppTheme
import org.example.myrecipes.core.presentation.ImagePicker
import org.example.myrecipes.di.AppModule
import org.example.myrecipes.screens.AssistantScreen
import org.example.myrecipes.screens.FavoriteScreen
import org.example.myrecipes.screens.HomeScreen


@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
    imagePicker: ImagePicker
) {
    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        BottomNavigationBar(appModule, imagePicker)
    }
}


@Composable
fun BottomNavigationBar(appModule: AppModule, imagePicker: ImagePicker) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Przepisy", "Ulubione", "Asystent AI")
    val icons = listOf(Icons.Filled.Receipt, Icons.Filled.Favorite, Icons.Filled.VolunteerActivism)

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> HomeScreen(appModule, imagePicker)
                1 -> FavoriteScreen(appModule, imagePicker)
                2 -> AssistantScreen()
            }
        }
    }
}





