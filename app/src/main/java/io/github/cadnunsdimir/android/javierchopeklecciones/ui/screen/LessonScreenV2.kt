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
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.TranslationExercise
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.enums.StatusWordGuesser
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LessonViewModel
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LoginUiViewModel


@Composable
fun LessonScreenV2(viewModel: LessonViewModel = viewModel(), loginViewModel: LoginUiViewModel) {

    val lessonState = viewModel.uiState.collectAsState()
    val login = loginViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadExercises(login.value.login)
    }

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(20.dp))
        LessonProgressBar(lessonState.value.percentualProgress)
        Spacer(Modifier.height(20.dp))
        Text("Lição TBD")
        Spacer(Modifier.height(10.dp))


        when(lessonState.value.lesson?.type){
            "translation" ->  TranslationExercise(
                question = "${lessonState.value.lesson?.question}",
                answer = lessonState.value.answer,
                viewModel = viewModel
            )
            "fill_blank" -> FillBlankExercise(
                question = "${lessonState.value.lesson?.question}",
                answer = lessonState.value.answer,
                viewModel = viewModel
            )
            else -> Text("Exercício do tipo ${lessonState.value.lesson?.type} não implementado")
        }

        if(lessonState.value.statusWordGuesser != StatusWordGuesser.DONE) {
            Button({
                viewModel.checkAnswer(login.value.login)
            }) {
                Text("Verificar")
            }
        } else{
            Text("${lessonState.value.message}! ${lessonState.value.correctAnswer}")
            NextQuestionButton(onProgress = {
                viewModel.nextQuestion()
            })
        }
    }
}





