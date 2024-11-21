import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ui.theme.MyShapes
import ui.theme.WorkSansTypography

private val LightColorScheme = lightColorScheme(
    primary = primaryColorLight,
    secondary = secondaryColorLight,
    background = Color(0xFF000000),
    surface = fieldColorLight,
    onPrimary = textColorLight,
    onSecondary = textColorContrastLight,
    onBackground = textColorDark,
    onSurface = textColorDark,
)

private val DarkColorScheme = darkColorScheme(
    primary = primaryColorDark,
    secondary = secondaryColorDark,
    background = fieldColorDark,
    surface = fieldColorDark,
    onPrimary = textColorDark,
    onSecondary = textColorContrastDark,
    onBackground = textColorLight,
    onSurface = textColorLight,
)

@Composable
fun CustomTheme(
    content: @Composable () -> Unit
) {

    val colors = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = WorkSansTypography(),
        shapes = MyShapes,
        content = content
    )
}
