package io.github.cadnunsdimir.android.javierchopeklecciones.ui.state

data class LoginState(
    val login: String = "",
    val password: String = "",
    val proficiencyLevel: String = "A1",
    val lessonCounter: Int = 1,
    val score: Int = 0,
    val formIsValid: Boolean = false,
)