package io.github.cadnunsdimir.android.javierchopeklecciones.app.dto

data class ProgressApiResponse(
    val currentLevel: ProficiencyLevel,
    val weeklyStreak: Int,
    val levelProgressPercentage: Double,
    val estimatedNextLevelDate: String? = null,
    val completedLessons: Int,
    val totalLessonsInLevel: Int
)