package org.example.myrecipes.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.example.myrecipes.ui.theme.AppTypography
import org.example.myrecipes.ui.theme.DarkColorScheme
import org.example.myrecipes.ui.theme.LightColorScheme

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme,
        typography = AppTypography,
        content = content
    )
}