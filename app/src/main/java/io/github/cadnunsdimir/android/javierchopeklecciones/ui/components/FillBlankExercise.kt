package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LessonViewModel

@Composable
fun FillBlankExercise(
    question: String,
    answer: String,
    viewModel: LessonViewModel
) {

    val parts = question.split("___")

    Column {

        Text(
            text = "Preencha a lacuna",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = parts.getOrNull(0) ?: "",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = answer,
                onValueChange = { viewModel.onAnswer(it) },
                singleLine = true,
                modifier = Modifier.widthIn(min = 80.dp, max = 160.dp),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("...") }
            )

            Text(
                text = parts.getOrNull(1) ?: "",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}