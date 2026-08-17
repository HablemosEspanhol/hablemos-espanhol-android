package io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.AnswerRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.ExerciseApiResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.NotificationService
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.lesson.ApiLessonRestClientV2
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.enums.StatusWordGuesser
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.state.LessonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LessonViewModel(application: Application): AndroidViewModel(application) {
    var restClient =  ApiLessonRestClientV2()
    private val _exercises = MutableStateFlow<List<ExerciseApiResponse>>(emptyList())
    val exercises: StateFlow<List<ExerciseApiResponse>> = _exercises
    private val _uiState = MutableStateFlow(LessonState())
    val uiState: StateFlow<LessonState> = _uiState.asStateFlow()
    val answers: MutableList<AnswerRequest> = mutableListOf()

    fun loadExercises(proficiencyLevel: String) {
        viewModelScope.launch {
            try {
                _uiState.value  = _uiState.value.copy(
                    isLoading = true
                )
                val result = restClient.fetchExercises()
                _exercises.value = result
                _uiState.value  = _uiState.value.copy(
                    percentualProgress = 0f,
                    exercise = _exercises.value[0],
                    exerciseIndex = 0,
                    level = proficiencyLevel,
                    completedLesson = false,
                    isLoading = false
                )
            } catch (e: Exception) {
                NotificationService.notify("Ocorreu um erro ao carregar: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun onAnswer(value: String) {
        _uiState.value = _uiState.value.copy(answer = value)
    }

    fun nextQuestion() {
        val lessonIndex = _uiState.value.exerciseIndex +1
        var lesson: ExerciseApiResponse? = null
        val answeredAllQuestions = lessonIndex >= exercises.value.size
        if(!answeredAllQuestions) {
            lesson = exercises.value[lessonIndex]
        }

        _uiState.value = _uiState.value.copy(
            answer = "",
            statusWordGuesser = StatusWordGuesser.NEW,
            exerciseIndex = lessonIndex,
            exercise = lesson ?: _uiState.value.exercise,
            percentualProgress = lessonIndex.toFloat() / exercises.value.size
        )

        if (answeredAllQuestions) {

            viewModelScope.launch {
                val response = restClient.submitAnswers(
                    answers = answers
                )

                _uiState.value = _uiState.value.copy(
                    completedLesson = true,
                    message = response.message,
                    level = response.newLevel,
                    score = response.accuracy
                )
            }
        }

    }

    fun checkAnswer() {
        viewModelScope.launch {
            try {
                val answerRequest = AnswerRequest(
                    "${_uiState.value.exercise?.id}",
                    _uiState.value.answer.trim()
                    )
                val response = restClient.checkAnswer(
                    exerciseId = answerRequest.exerciseId,
                    answer = answerRequest.answer
                )

                answers.add(answerRequest)

                println("Resultado: ${response.message}")
                _uiState.value = _uiState.value.copy(
                    message = response.message,
                    correctAnswer = response.correctAnswer,
                    statusWordGuesser = StatusWordGuesser.DONE
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}