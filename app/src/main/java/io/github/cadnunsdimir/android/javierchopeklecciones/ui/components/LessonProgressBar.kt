package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun LessonProgressBar(percentualProgress: Float) {
    LinearProgressIndicator(
        progress = { percentualProgress.coerceIn(0f, 1f) },
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
        strokeCap = StrokeCap.Round
    )
}