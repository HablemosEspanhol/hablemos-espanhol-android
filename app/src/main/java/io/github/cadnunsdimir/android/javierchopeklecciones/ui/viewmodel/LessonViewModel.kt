package io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.ExerciseApiResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.ApiLessonRestClientV2
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.enums.StatusWordGuesser
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.state.LessonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LessonViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>()
    var restClient =  ApiLessonRestClientV2()
    private val _exercises = MutableStateFlow<List<ExerciseApiResponse>>(emptyList())
    val exercises: StateFlow<List<ExerciseApiResponse>> = _exercises

    fun loadExercises(username: String) {
        viewModelScope.launch {
            try {
                val result = restClient.fetchExercises(username)
                _exercises.value = result
                _uiState.value  = _uiState.value.copy(
                    lesson = _exercises.value[0],
                    lessonIndex = 0,
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onAnswer(value: String) {
        _uiState.value = _uiState.value.copy(answer = value)
    }

    fun nextQuestion() {
        val lessonIndex = _uiState.value.lessonIndex +1
        _uiState.value = _uiState.value.copy(
            answer = "",
            statusWordGuesser = StatusWordGuesser.NEW,
            lessonIndex = lessonIndex,
            lesson = exercises.value[lessonIndex],
            percentualProgress = lessonIndex.toFloat() / exercises.value.size
        )
    }

    fun checkAnswer(username: String) {
        viewModelScope.launch {
            try {
                val response = restClient.checkAnswer(
                    username = username,
                    exerciseId = "${_uiState.value.lesson?.id}",
                    userAnswer = _uiState.value.answer.trim(),
                    answer = _uiState.value.lesson?.question?:""
                )

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

    private val _uiState = MutableStateFlow(LessonState())
    val uiState: StateFlow<LessonState> = _uiState.asStateFlow()
}