package org.example.project.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun TodoTheme(
    content: @Composable () -> Unit
) {

    val darkTheme = isSystemInDarkTheme()

    MaterialTheme(
        colorScheme =
            if (darkTheme)
                darkColorScheme()
            else
                lightColorScheme(),
        typography = AppTypography,
        content = content
    )
}