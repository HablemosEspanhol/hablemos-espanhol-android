package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.enums.StatusWordGuesser

@Composable
fun WordGuesserComponent(status: StatusWordGuesser, expectedText: String) {
    val color = mapOf(
        StatusWordGuesser.WRONG to MaterialTheme.colorScheme.error,
        StatusWordGuesser.DONE to Color.Green,
    )[status]?: MaterialTheme.colorScheme.onSurface
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(expectedText,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
            fontSize = 22.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            color = color
        )
    }
}