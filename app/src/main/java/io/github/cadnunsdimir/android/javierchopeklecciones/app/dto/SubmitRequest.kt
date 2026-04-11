package io.github.cadnunsdimir.android.javierchopeklecciones.app.dto

data class SubmitRequest(
    val username: String,
    val answers: List<AnswerRequest>
)