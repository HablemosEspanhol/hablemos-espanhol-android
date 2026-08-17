package io.github.cadnunsdimir.android.javierchopeklecciones.app.service.lesson

import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.LessonWithQuestions
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.Question
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.NotificationService
import org.json.JSONObject

class ApiLessonRestRestClient: BaseLessonRestClient() {

    override fun getLessonFromRemote(): Map<Int, LessonWithQuestions> {
        val totalLessons = 10
        val lessons = mutableMapOf<Int, LessonWithQuestions>()
        for (id in 1..totalLessons){
            var success = false
            while (!success) {
                try {
                    val lesson = getLesson(id)
                    lessons.put(id, lesson)
                    success = true
                } catch (ex: Exception) {
                    NotificationService.notify("Erro ao carregar Lição $id: ${ex.message}");
                }
            }
        }
        return lessons
    }

    private fun getLesson(id: Int): LessonWithQuestions {
        val json = get("http://192.168.15.3:3000")
        val objectResponse = JSONObject(json)
        val lesson = objectResponse.getJSONArray("data")
        val questions = List(lesson.length()) { i ->
            val obj = lesson.getJSONObject(i)
            Question(
                i,
                id,
                obj.getString("front"),
                obj.getString("back")
            )
        }
        return LessonWithQuestions(Lesson(id, "title"), questions)
    }
}