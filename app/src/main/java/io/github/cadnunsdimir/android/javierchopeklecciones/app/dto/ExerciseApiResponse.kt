package io.github.cadnunsdimir.android.javierchopeklecciones.app.dto

data class ExerciseApiResponse(
    val palavra: String,
    val type: String,
    val question: String,
    val options: List<String>? = null,
    val id: String
)