package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NextQuestionButton(onProgress: () -> Unit) {
    Row (
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                onProgress()
            }
        ) {
            Text("Próxima Palavra")
        }
    }
}