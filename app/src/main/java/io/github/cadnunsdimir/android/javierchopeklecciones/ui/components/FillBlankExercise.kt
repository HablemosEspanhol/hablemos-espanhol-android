package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LessonViewModel

@Composable
fun FillBlankExercise(question: String, answer: String, viewModel: LessonViewModel) {
    Text("Preencha a lacuna")
    Spacer(Modifier.Companion.height(20.dp))
    Text(question)
    Spacer(Modifier.Companion.height(20.dp))
    OutlinedTextField(
        value = answer,
        onValueChange = { viewModel.onAnswer(it) },
        singleLine = true,
        modifier = Modifier.width(80.dp)
    )
    Spacer(Modifier.Companion.height(20.dp))
}