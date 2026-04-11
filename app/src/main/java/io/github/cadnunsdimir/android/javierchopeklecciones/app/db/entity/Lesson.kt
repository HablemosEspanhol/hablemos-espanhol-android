package io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Lesson(
    @PrimaryKey
    val id: Int,
    val lessonTitle: String)

data class LessonWithQuestions(
    @Embedded
    val lesson: Lesson,

    @Relation(
        parentColumn = "id",
        entityColumn = "lessonId"
    )
    var questions: List<Question>
) {
    fun getNewPhrase(expectedText: String? = null): Question {
        val expectedQuestion = questions.find { it.phraseSpanish == expectedText }
        val indexNextWord = if(expectedQuestion == null) 0 else questions.indexOf(expectedQuestion) + 1;
        return if(indexNextWord < questions.size) questions[indexNextWord] else questions[0]
    }

    fun randomizeQuestions() {
        questions = questions.shuffled()
    }
}

