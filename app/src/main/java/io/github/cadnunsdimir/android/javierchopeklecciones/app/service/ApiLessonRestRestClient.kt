package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Question
import org.json.JSONObject

class ApiLessonRestRestClient: BaseLessonRestClient() {

    override fun getLessonFromRemote(): Map<Int, Lesson> {
        val totalLessons = 10
        val lessons = mutableMapOf<Int, Lesson>()
        for (id in 1..totalLessons){
            val lesson = getLesson(id);
            lessons.put(id, lesson);
        }
        return lessons
    }

    private fun getLesson(id: Int): Lesson {
        val json = get("http://192.168.15.3:3000")
        val objectResponse = JSONObject(json)
        val lesson = objectResponse.getJSONArray("data")
        val questions = List(lesson.length()) { i ->
            val obj = lesson.getJSONObject(i)
            Question(
                i,
                obj.getString("front"),
                obj.getString("back")
            )
        }
        return Lesson(id, questions)
    }
}