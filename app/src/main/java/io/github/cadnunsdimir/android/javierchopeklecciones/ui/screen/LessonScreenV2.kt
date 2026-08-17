package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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

    if(lesson.exercise == null) {
        LaunchedEffect(Unit) {
            viewModel.loadExercises(login.proficiencyLevel)
        }
    }

    if (lesson.isLoading == true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if(lesson.completedLesson){
        loginViewModel.updateProficiencyLevelAndScore(lesson.level, lesson.score)
        FinishLesson(
            score = lesson.score,
            message = lesson.message,
            onNextLesson = {
                viewModel.loadExercises(lesson.level)
            }
        )
    }

    if(lesson.completedLesson) return
    if(lesson.exercise == null) return
    if(lesson.isLoading == true) return

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

        Spacer(modifier = Modifier.height(10.dp))

        if (lesson.statusWordGuesser != StatusWordGuesser.DONE) {
            PrimaryButton("Verificar") {
                viewModel.checkAnswer()
            }
        } else {
            PrimaryButton("Continuar") {
                viewModel.nextQuestion()
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
                .padding(10.dp)
                .verticalScroll(ScrollState(0))
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



