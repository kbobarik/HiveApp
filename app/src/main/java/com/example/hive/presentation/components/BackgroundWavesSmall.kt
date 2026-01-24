package com.example.hive.presentation.components

import android.graphics.Canvas
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.hive.ui.theme.PurpleDark
import com.example.hive.ui.theme.PurpleLight
import com.example.hive.ui.theme.PurpleMain


@Composable
fun BackgroundWavesSmall() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        val width = size.width

        drawCircle(
            color = PurpleDark,
            radius = width,
            center = Offset(width / 2, -200f)
        )

        drawCircle(
            color = PurpleMain,
            radius = width,
            center = Offset(width / 2, -140f)
        )

        drawCircle(
            color = PurpleLight,
            radius = width,
            center = Offset(width / 2, -80f)
        )
    }
}
