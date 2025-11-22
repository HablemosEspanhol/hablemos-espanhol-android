package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import android.content.Context
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Question
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.collections.set

object LessonAssetReader {
    fun readCsvFromAssets(context: Context, fileName: String): Map<Int, Lesson> {
        val assetManager = context.assets
        val lines = mutableListOf<String>()

        try {
            val inputStream = assetManager.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (!line.isNullOrBlank()) {
                    lines.add(line!!)
                }
            }
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return transform(lines)
    }

    fun transform(csv: List<String>) : Map<Int, Lesson> {
        val lessons = mutableMapOf<Int, Lesson>()
        csv.subList(1, csv.size - 1)
            .forEach {
                val dataset = it
                    .replace("\"", "")
                    .split(",")
                val lessonId = dataset[0].toInt()
                var lesson = lessons[lessonId]
                if(lesson == null) {
                    lesson = Lesson(lessonId, listOf())
                    lessons[lessonId] = lesson
                }
                val question = Question(
                    dataset[1].toInt(),
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