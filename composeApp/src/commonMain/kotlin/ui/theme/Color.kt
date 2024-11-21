import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Thème sombre
val primaryColorDark = Color(0xFF1c1d1f)
val secondaryColorDark = Color(0xFF2e3133)
val fieldColorDark = Color(0xFF202224)
val textColorDark = Color(0xFFFFFFFF)
val textColorContrastDark = Color(0xFF071220)
val contrastColorDark = Color(0xFFFFFFFF)

// Thème clair
val primaryColorLight = Color(0xFFF9F9F9)
val secondaryColorLight = Color(0xFFEBECE9)
val fieldColorLight = Color(0xFFFFFFFF)
val textColorLight = Color(0xFF071220)
val textColorContrastLight = Color(0xFFFFFFFF)
val contrastColorLight = Color(0xFF000000)

val primaryColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF1c1d1f)
    else Color(0xFFF9F9F9)

val secondaryColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF2e3133)
    else Color(0xFFEBECE9)

val fieldColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF202224)
    else Color(0xFFFFFFFF)

val textColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFFFFFFF)
    else Color(0xFF071220)

val textColorContrast
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF071220)
    else Color(0xFFFFFFFF)

val contrastColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFFFFFFF)
    else Color(0xFF000000)


val fontGrayColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF808080)
    else Color(0xFF808080)