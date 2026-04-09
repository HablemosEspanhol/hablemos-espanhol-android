package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import android.content.Context
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.LessonWithQuestions
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.Question
import java.io.BufferedReader
import java.io.InputStreamReader

object LessonAssetReader {
    fun readCsvFromAssets(context: Context, fileName: String): Map<Int, LessonWithQuestions> {
        val assetManager = context.assets
        val lines = mutableListOf<String>()

        try {
            val inputStream = assetManager.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (!line.isNullOrBlank()) {
                    lines.add(line)
                }
            }
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return transform(lines)
    }

    fun transform(csv: List<String>) : Map<Int, LessonWithQuestions> {
        val lessons = mutableMapOf<Int, LessonWithQuestions>()
        csv.subList(1, csv.size - 1)
            .forEach {
                val dataset = it
                    .replace("\"", "")
                    .split(",")
                val lessonId = dataset[0].toInt()
                var lesson = lessons[lessonId]
                if(lesson == null) {
                    lesson = LessonWithQuestions(Lesson(lessonId, "title"), listOf())
                    lessons[lessonId] = lesson
                }
                val question = Question(
                    dataset[1].toInt(),
                    lesson.lesson.id,
                    dataset[2],
                    dataset[3]
                )
                lesson.questions = listOf(
                    *lesson.questions.toTypedArray(),
                    question
                )
            }
        return lessons
    }
}