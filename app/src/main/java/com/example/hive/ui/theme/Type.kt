package com.example.hive.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.hive.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Nimbus = FontFamily(
    Font(
        R.font.nimbus_sans_round_ultra_light,
        weight = FontWeight.Thin,
        style = FontStyle.Normal
    ),
    Font(
        R.font.nimbus_sans_round_ultra_light_italic,
        weight = FontWeight.Thin,
        style = FontStyle.Italic
    ),

    // Light
    Font(
        R.font.nimbus_sans_round_light,
        weight = FontWeight.Light,
        style = FontStyle.Normal
    ),
    Font(
        R.font.nimbus_sans_round_light_italic,
        weight = FontWeight.Light,
        style = FontStyle.Italic
    ),

    // Regular
    Font(
        R.font.nimbus_sans_round,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(
        R.font.nimbus_sans_round_regular_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),

    // Medium
    Font(
        R.font.nimbus_sans_round_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        R.font.nimbus_sans_round_medium_italic,
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    ),

    // SemiBold
    Font(
        R.font.nimbus_sans_round_semi_bold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        R.font.nimbus_sans_round_semi_bold_italic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),

    // Bold
    Font(
        R.font.nimbus_sans_round_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        R.font.nimbus_sans_round_bold_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ),

    // Black / Heavy
    Font(
        R.font.nimbus_sans_round_black,
        weight = FontWeight.Black,
        style = FontStyle.Normal
    ),
    Font(
        R.font.nimbus_sans_round_black_italic,
        weight = FontWeight.Black,
        style = FontStyle.Italic
    ),

    Font(
        R.font.nimbus_sans_round_heavy,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Normal
    ),
    Font(
        R.font.nimbus_sans_round_heavy_italic,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Italic
    )

)