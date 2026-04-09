package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.FillBlankExercise
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.MultipleChoiceExercise
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.TranslationExercise
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.enums.StatusWordGuesser
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LessonViewModel
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LoginUiViewModel


@Composable
fun LessonScreenV2(viewModel: LessonViewModel = viewModel(), loginViewModel: LoginUiViewModel) {

    val exercise = viewModel.uiState.collectAsState().value
    val login = loginViewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadExercises(
            login.login,
            login.proficiencyLevel)
    }

    if (exercise.exercise != null) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            LessonProgressBar(exercise.percentualProgress)
            Spacer(Modifier.height(20.dp))
            Text("Lição ${login.lessonCounter} - Nível ${login.proficiencyLevel}")
            Spacer(Modifier.height(10.dp))

            when (exercise.exercise.type) {
                "translation" -> TranslationExercise(
                    question = exercise.question,
                    answer = exercise.answer,
                    viewModel = viewModel
                )

                "fill_blank" -> FillBlankExercise(
                    question = exercise.exercise.question,
                    answer = exercise.answer,
                    viewModel = viewModel
                )

                "multiple_choice" -> MultipleChoiceExercise(
                    question = exercise.exercise.question,
                    options = exercise.exercise.options as List<String>,
                    selectedOption = exercise.answer,
                    viewModel = viewModel
                )

                else -> Text("Exercício do tipo ${exercise.exercise.type} não implementado")
            }

            if (exercise.statusWordGuesser != StatusWordGuesser.DONE) {
                Button({
                    viewModel.checkAnswer(login.login)
                }) {
                    Text("Verificar")
                }
            } else {
                val status = if(exercise.message?.contains("incorreta") ?: false)
                StatusWordGuesser.WRONG else StatusWordGuesser.DONE
                Text("${exercise.message}")
                WordGuesserComponent(status, exercise.correctAnswer as String)
                Spacer(Modifier.height(20.dp))
                NextQuestionButton(onProgress = {
                    viewModel.nextQuestion(login.login)
                })
            }
        }
    }

    if(exercise.completedLesson){
        loginViewModel.updateProficiencyLevel(exercise.level)
        FinishLesson(score = exercise.score,
            onNextLesson = {
                viewModel.loadExercises(login.login, exercise.level)
            }
        )
    }
}





