package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LessonViewModel

@Composable
fun TranslationExercise(
    question: String,
    answer: String,
    viewModel: LessonViewModel
) {

    Column {

        Text(
            text = "Traduza para português",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = question,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = answer,
            onValueChange = { viewModel.onAnswer(it) },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text("Digite sua tradução...")
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

