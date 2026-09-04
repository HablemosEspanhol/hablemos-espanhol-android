package io.github.cadnunsdimir.android.javierchopeklecciones.app.service.userprogression

import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.ProficiencyLevel
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.ProgressApiResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.state.CefrLevel
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.state.UserProgress
import java.text.SimpleDateFormat
import java.util.Locale

class UserProgressRepository(private val api: UserProgressionRestContract) {
    suspend fun fetchUserProgress(): Result<UserProgress> {
        return runCatching {
            val response = api.getProgress()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toDomainModel()
            } else {
                throw Exception("Erro na requisição: ${response.code()}")
            }
        }
    }
}

private fun ProgressApiResponse.toDomainModel(): UserProgress {
    return UserProgress(
        this.currentLevel.toCefrLevel(),
        this.weeklyStreak *7,
        this.weeklyStreak*7,
        this.completedLessons,
        this.totalLessonsInLevel,
        formatDate(this.estimatedNextLevelDate))
}

fun ProficiencyLevel.toCefrLevel(): CefrLevel {
    return CefrLevel.valueOf(this.name)
}

fun formatDate(input: String?, inputPattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", outputPattern: String= "dd/MMM/yy"): String {
    try {
        if (input.isNullOrBlank()) return "invalidDate"
        val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
        val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
        val date = inputFormat.parse(input) ?: return input
        return outputFormat.format(date)
    } catch (ex: Exception){
        return ex.message.toString()
    }

}
