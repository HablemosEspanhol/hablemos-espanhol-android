package io.github.cadnunsdimir.android.javierchopeklecciones.app.entity

import androidx.room.Entity

@Entity
data class Lesson(val id: Int, val lessonTitle: String, var questions: List<Question>) {
    fun getNewPhrase(expectedText: String? = null): Question {
        val expectedQuestion = questions.find { it.phraseSpanish == expectedText }
        val indexNextWord = if(expectedQuestion == null) 0 else questions.indexOf(expectedQuestion) + 1;
        return if(indexNextWord < questions.size) questions[indexNextWord] else questions[0]
    }

    fun randomizeQuestions() {
        questions = questions.shuffled()
    }
}
