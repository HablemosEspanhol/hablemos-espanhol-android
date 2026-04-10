package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LessonViewModel

@Composable
fun MultipleChoiceExercise(
    question: String,
    selectedOption: String,
    options: List<String>,
    viewModel: LessonViewModel
) {

    Column {

        // 🧠 INSTRUÇÃO
        Text(
            text = "Escolha a opção correta",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🎯 PERGUNTA (FOCO)
        Text(
            text = question,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 📋 OPÇÕES
        options.forEach { option ->

            val isSelected = option == selectedOption

            val containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant

            val contentColor = if (isSelected)
                MaterialTheme.colorScheme.onPrimaryContainer
            else
                MaterialTheme.colorScheme.onSurface

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        viewModel.onAnswer(option)
                    },
                colors = CardDefaults.cardColors(containerColor = containerColor),
                shape = RoundedCornerShape(16.dp)
            ) {

                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = isSelected,
                        onClick = {
                            viewModel.onAnswer(option)
                        }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge,
                        color = contentColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}