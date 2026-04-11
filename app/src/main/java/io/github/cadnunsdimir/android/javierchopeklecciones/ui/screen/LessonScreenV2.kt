package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.FillBlankExercise
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.FinishLesson
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.LessonProgressBar
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.MultipleChoiceExercise
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.TranslationExercise
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.WordGuesserComponent
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.enums.StatusWordGuesser
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LessonViewModel
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LoginUiViewModel

@Composable
fun LessonScreenV2(
    viewModel: LessonViewModel = viewModel(),
    loginViewModel: LoginUiViewModel
) {

    val lesson = viewModel.uiState.collectAsState().value
    val login = loginViewModel.uiState.collectAsState().value

    if(lesson.exercise == null && !lesson.completedLesson) {
        LaunchedEffect(Unit) {
            viewModel.loadExercises(login.login, login.proficiencyLevel)
        }
    }

    if(lesson.completedLesson){
        loginViewModel.updateProficiencyLevelAndScore(lesson.level, lesson.score)
        FinishLesson(
            score = lesson.score,
            message = lesson.message,
            onNextLesson = {
                viewModel.loadExercises(login.login, lesson.level)
            }
        )
    }

    if (lesson.exercise == null) return

    val question = lesson.exercise.question

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        LessonProgressBar(lesson.percentualProgress)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Lição ${login.lessonCounter} - Nível ${login.proficiencyLevel}",
        )

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {

            ExerciseCard {

                when (lesson.exercise.type) {

                    "translation" -> TranslationExercise(
                        question = question,
                        answer = lesson.answer,
                        viewModel = viewModel
                    )

                    "fill_blank" -> FillBlankExercise(
                        question = question,
                        answer = lesson.answer,
                        viewModel = viewModel
                    )

                    "multiple_choice" -> MultipleChoiceExercise(
                        question = question,
                        options = lesson.exercise.options as List<String>,
                        selectedOption = lesson.answer,
                        viewModel = viewModel
                    )
                }
            }
        }

        if (lesson.statusWordGuesser == StatusWordGuesser.DONE) {
            val status = if (lesson.message?.contains("incorreta") == true)
                StatusWordGuesser.WRONG else StatusWordGuesser.DONE

            WordGuesserComponent(
                status = status,
                correctWord = lesson.correctAnswer as String
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (lesson.statusWordGuesser != StatusWordGuesser.DONE) {
            PrimaryButton("Verificar") {
                viewModel.checkAnswer(login.login)
            }
        } else {
            PrimaryButton("Continuar") {
                viewModel.nextQuestion(login.login)
            }
        }
    }
}
@Composable
fun ExerciseCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            content()
        }
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(text)
    }
}



