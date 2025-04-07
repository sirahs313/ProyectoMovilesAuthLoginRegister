package com.example.MovilesSUax.ui.theme

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

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006D40),       // Verde oscuro principal
    onPrimary = Color.White,           // Texto sobre elementos primarios
    primaryContainer = Color(0xFF95F0B8), // Fondo para elementos primarios
    onPrimaryContainer = Color(0xFF002110), // Texto sobre contenedor primario

    secondary = Color(0xFF4A6357),     // Verde secundario
    onSecondary = Color.White,         // Texto sobre secundario
    secondaryContainer = Color(0xFFCCE8D7), // Fondo secundario
    onSecondaryContainer = Color(0xFF062019), // Texto sobre contenedor secundario

    tertiary = Color(0xFF3D8476),      // Verde azulado terciario
    onTertiary = Color.White,          // Texto sobre terciario
    tertiaryContainer = Color(0xFFC1E9DD), // Fondo terciario
    onTertiaryContainer = Color(0xFF00201A), // Texto sobre contenedor terciario

    error = Color(0xFFBA1A1A),         // Color para errores
    onError = Color.White,             // Texto sobre error
    errorContainer = Color(0xFFFFDAD6), // Fondo de error
    onErrorContainer = Color(0xFF410002), // Texto sobre contenedor de error

    background = Color(0xFFFBFDF8),    // Color de fondo claro
    onBackground = Color(0xFF191C1A),  // Texto sobre fondo claro

    surface = Color(0xFFFBFDF8),       // Color de superficie claro
    onSurface = Color(0xFF191C1A),     // Texto sobre superficie clara
    surfaceVariant = Color(0xFFDCE5DB), // Variante de superficie clara
    onSurfaceVariant = Color(0xFF404943), // Texto sobre variante de superficie clara

    outline = Color(0xFF707972),       // Color para bordes
    inverseOnSurface = Color(0xFFEFF1ED), // Texto inverso sobre superficie
    inverseSurface = Color(0xFF2E312F), // Superficie inversa
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF78D998),       // Verde claro principal (oscuro)
    onPrimary = Color(0xFF00391F),     // Texto sobre primario oscuro
    primaryContainer = Color(0xFF00522F), // Contenedor primario oscuro
    onPrimaryContainer = Color(0xFF95F0B8), // Texto sobre contenedor primario oscuro

    secondary = Color(0xFFB1CCBD),     // Verde claro secundario (oscuro)
    onSecondary = Color(0xFF1D3529),   // Texto sobre secundario oscuro
    secondaryContainer = Color(0xFF334B3F), // Contenedor secundario oscuro
    onSecondaryContainer = Color(0xFFCCE8D7), // Texto sobre contenedor secundario oscuro

    tertiary = Color(0xFFA5CDBF),      // Verde azulado claro terciario (oscuro)
    onTertiary = Color(0xFF02372C),    // Texto sobre terciario oscuro
    tertiaryContainer = Color(0xFF214E43), // Contenedor terciario oscuro
    onTertiaryContainer = Color(0xFFC1E9DD), // Texto sobre contenedor terciario oscuro

    error = Color(0xFFFFB4AB),         // Color de error (oscuro)
    onError = Color(0xFF690005),       // Texto sobre error oscuro
    errorContainer = Color(0xFF93000A), // Contenedor de error oscuro
    onErrorContainer = Color(0xFFFFDAD6), // Texto sobre contenedor de error oscuro

    background = Color(0xFF191C1A),    // Fondo oscuro
    onBackground = Color(0xFFE1E3DF),  // Texto sobre fondo oscuro

    surface = Color(0xFF191C1A),       // Superficie oscura
    onSurface = Color(0xFFE1E3DF),     // Texto sobre superficie oscura
    surfaceVariant = Color(0xFF404943), // Variante de superficie oscura
    onSurfaceVariant = Color(0xFFBFC9C0), // Texto sobre variante de superficie oscura

    outline = Color(0xFF89938B),       // Borde oscuro
    inverseOnSurface = Color(0xFF191C1A), // Texto inverso oscuro
    inverseSurface = Color(0xFFE1E3DF), // Superficie inversa oscura
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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