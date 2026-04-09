package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun LessonProgressBar(percentualProgress: Float) {
    LinearProgressIndicator(
        progress = { percentualProgress }, // Ex: 0.75f para 75%
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
        color = Color.Blue,
        trackColor = Color.LightGray,
        strokeCap = StrokeCap.Round,
        gapSize = (-10).dp,
    )
}