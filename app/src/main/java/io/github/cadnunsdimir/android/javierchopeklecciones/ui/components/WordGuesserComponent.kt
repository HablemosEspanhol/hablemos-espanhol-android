package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.enums.StatusWordGuesser
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.theme.onSuccessContainer
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.theme.successContainer

@Composable
fun WordGuesserComponent(
    status: StatusWordGuesser,
    correctWord: String
) {
    val containerColor = when (status) {
        StatusWordGuesser.WRONG -> MaterialTheme.colorScheme.errorContainer
        StatusWordGuesser.DONE -> successContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = when (status) {
        StatusWordGuesser.WRONG -> MaterialTheme.colorScheme.onErrorContainer
        StatusWordGuesser.DONE -> onSuccessContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = if (status == StatusWordGuesser.WRONG)
                    "✖ Resposta correta:"
                else
                    "✔ Correto!",
                style = MaterialTheme.typography.labelLarge,
                color = contentColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = correctWord,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )
        }
    }
}