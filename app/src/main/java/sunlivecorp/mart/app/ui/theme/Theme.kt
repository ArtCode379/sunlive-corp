package sunlivecorp.mart.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SunliveColorScheme = lightColorScheme(
    primary = CharcoalPrimary,
    onPrimary = OffWhiteBackground,
    primaryContainer = TextPrimary,
    onPrimaryContainer = OffWhiteBackground,
    secondary = BronzeAccent,
    onSecondary = CharcoalPrimary,
    secondaryContainer = BronzeLight,
    onSecondaryContainer = CharcoalPrimary,
    tertiary = BronzeLight,
    onTertiary = CharcoalPrimary,
    background = OffWhiteBackground,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariantCream,
    onSurfaceVariant = TextMuted,
    outline = BorderLight,
    error = ErrorRed,
    onError = SurfaceWhite,
)

@Composable
fun ProductAppSkeletonTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SunliveColorScheme,
        typography = Typography,
        content = content
    )
}
