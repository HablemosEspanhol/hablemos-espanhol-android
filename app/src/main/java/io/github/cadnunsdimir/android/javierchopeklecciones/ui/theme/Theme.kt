package io.github.cadnunsdimir.android.javierchopeklecciones.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(

    primary = Color(0xFF1E88E5),          // azul confiável
    onPrimary = Color.White,

    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = Color(0xFF0D47A1),

    secondary = Color(0xFF546E7A),
    onSecondary = Color.White,

    background = Color(0xFFF9FAFB),
    onBackground = Color(0xFF1C1B1F),

    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),

    error = Color(0xFFD32F2F),
    onError = Color.White,

    errorContainer = Color(0xFFFDECEA),
    onErrorContainer = Color(0xFF7F1D1D)
)

@Composable
fun JavierChopekLeccionesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}