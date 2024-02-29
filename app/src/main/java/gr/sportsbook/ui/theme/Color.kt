package gr.sportsbook.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// define your colors for dark theme
val clear_dark = Color(0xFFA05162)
val dark_btn = Color(0xFF222427)

// define your colors for dark theme
val light_btn = Color(android.graphics.Color.parseColor("#E9F0F4"))
val light_bg = Color(android.graphics.Color.parseColor("#F6F8F9"))
val clear_light = Color(0xFFF1C8D1)

val BlueGray50 = Color(0xFFECEFF1)
val BlueGray100 = Color(0xFFCFD8DC)
val BlueGray200 = Color(0xFFB0BEC5)
val BlueGray300 = Color(0xFF90A4AE)
val BlueGray400 = Color(0xFF78909C)
val BlueGray500 = Color(0xFF607D8B)
val BlueGray600 = Color(0xFF546E7A)
val BlueGray700 = Color(0xFF455A64)
val BlueGray800 = Color(0xFF37474F)
val BlueGray900 = Color(0xFF263238)

val Teal50 = Color(0xFFE0F2F1)
val Teal100 = Color(0xFFB2DFDB)
val Teal200 = Color(0xFF80CBC4)
val Teal300 = Color(0xFF4DB6AC)
val Teal400 = Color(0xFF26A69A)
val Teal500 = Color(0xFF009688)
val Teal600 = Color(0xFF00897B)
val Teal700 = Color(0xFF00796B)
val Teal800 = Color(0xFF00695C)
val Teal900 = Color(0xFF004D40)
val TealA100 = Color(0xFFA7FFEB)
val TealA200 = Color(0xFF64FFDA)
val TealA400 = Color(0xFF1DE9B6)
val TealA700 = Color(0xFF00BFA5)


sealed class ThemeColors(
    val bacground: Color,
    val surafce: Color,
    val primary: Color,
    val text: Color
)  {
    object Night: ThemeColors(
        bacground = Color.Black,
        surafce = dark_btn,
        primary = clear_dark,
        text = Color.White
    )
    object Day: ThemeColors(
        bacground = light_bg,
        surafce = light_btn,
        primary = clear_light,
        text = Color.Black
    )
}
