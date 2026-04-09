package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LessonViewModel

@Composable
fun MultipleChoiceExercise(question: String, options: List<String>, viewModel: LessonViewModel) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    Text("Escolha a opção correta")
    Spacer(Modifier.height(20.dp))
    Text(question)
    Spacer(Modifier.height(20.dp))

    Column {
        options.forEach { option ->
            val isSelected = option == selectedOption
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedOption = option
                        viewModel.onAnswer(option)
                    }
                    .padding(12.dp)
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = {
                        selectedOption = option
                        viewModel.onAnswer(option)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option)
            }
        }
    }
    Spacer(Modifier.height(20.dp))
}