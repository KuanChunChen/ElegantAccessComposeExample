package elegant.access.compose.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import elegant.access.compose.example.R

@Composable
private fun EADarkColorScheme() = darkColorScheme(
    primary = colorResource(R.color.primary),
    onPrimary = colorResource(R.color.onPrimary),
    primaryContainer = colorResource(R.color.primaryContainer),
    onPrimaryContainer = colorResource(R.color.onPrimaryContainer),
    secondary = colorResource(R.color.secondary),
    onSecondary = colorResource(R.color.onSecondary),
    tertiary = colorResource(R.color.tertiary),
    background = colorResource(R.color.background),
    onBackground = colorResource(R.color.onBackground),
    surface = colorResource(R.color.surface),
    onSurface = colorResource(R.color.onSurface),
    surfaceVariant = colorResource(R.color.surfaceVariant),
    onSurfaceVariant = colorResource(R.color.onSurfaceVariant),
    surfaceDim = colorResource(R.color.surfaceDim),
    error = colorResource(R.color.error),
    onError = colorResource(R.color.onError),
    outlineVariant = colorResource(R.color.outlineVariant),
    surfaceContainerHigh = colorResource(R.color.surfaceContainerHigh),
    surfaceContainer = colorResource(R.color.surfaceContainer),
    surfaceContainerLow = colorResource(R.color.surfaceContainerLow),
    inverseSurface = colorResource(R.color.inverseSurface)
)

@Composable
private fun EALightColorScheme() = lightColorScheme(
    primary = colorResource(R.color.primary),
    onPrimary = colorResource(R.color.onPrimary),
    primaryContainer = colorResource(R.color.primaryContainer),
    onPrimaryContainer = colorResource(R.color.onPrimaryContainer),
    secondary = colorResource(R.color.secondary),
    onSecondary = colorResource(R.color.onSecondary),
    tertiary = colorResource(R.color.tertiary),
    background = colorResource(R.color.background),
    onBackground = colorResource(R.color.onBackground),
    surface = colorResource(R.color.surface),
    onSurface = colorResource(R.color.onSurface),
    surfaceVariant = colorResource(R.color.surfaceVariant),
    onSurfaceVariant = colorResource(R.color.onSurfaceVariant),
    surfaceDim = colorResource(R.color.surfaceDim),
    error = colorResource(R.color.error),
    onError = colorResource(R.color.onError),
    outlineVariant = colorResource(R.color.outlineVariant),
    surfaceContainerHigh = colorResource(R.color.surfaceContainerHigh),
    surfaceContainer = colorResource(R.color.surfaceContainer),
    surfaceContainerLow = colorResource(R.color.surfaceContainerLow),
    inverseSurface = colorResource(R.color.inverseSurface)
)

val shapes = Shapes(
    extraLarge = RoundedCornerShape(30.dp),
    large = RoundedCornerShape(24.dp),
    medium = RoundedCornerShape(16.dp),
    small = RoundedCornerShape(8.dp),
    extraSmall = RoundedCornerShape(4.dp)
)

@Composable
fun ElegantAccessComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> EADarkColorScheme()
        else -> EALightColorScheme()
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = createTypography(colorScheme),
        shapes = shapes,
        content = content
    )
}