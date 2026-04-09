package io.github.cadnunsdimir.android.javierchopeklecciones.ui.state

import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.ExerciseApiResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.components.enums.StatusWordGuesser
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen.WAITING

data class LessonState(
    val percentualProgress: Float = 0f,
    val lesson: ExerciseApiResponse? = null,
    val statusWordGuesser: StatusWordGuesser = StatusWordGuesser.NEW,
    val type: String = "",
    val question: String = "",
    val buttonText: String = WAITING,
    val answer: String = "",
    val lessonIndex: Int = 0,
    val message: String? = "",
    val correctAnswer: String? = "",
    val completedLesson: Boolean = false,
    val score: Int = 20
)