package io.github.cadnunsdimir.android.javierchopeklecciones.ui.state

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object LoggedOut : HomeUiState
    data class LoggedIn(val userProgress: UserProgress) : HomeUiState
}

enum class CefrLevel(val label: String, val shortName: String) {
    A1("Acceso", "A1"),
    A2("Plataforma", "A2"),
    B1("Umbral", "B1"),
    B2("Avanzado", "B2"),
    C1("Dominio", "C1"),
    C2("Maestría", "C2")
}

data class UserProgress(
    val currentLevel: CefrLevel,
    val totalStudyDays: Int,
    val streakCount: Int,
    val completedLessonsInCurrentLevel: Int,
    val totalLessonsInCurrentLevel: Int,
    val estimatedB1CompletionDate: String
)
