package io.github.cadnunsdimir.android.javierchopeklecciones.app.repository

import android.content.Context
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Question
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.LessonAssetReader

class LessonRepository {
    companion object {
        private var db: Map <Int, Lesson> = mapOf<Int, Lesson>()

        fun getLesson(id: Int): Lesson? = db[id]
        fun getAllLessons(): List<Lesson> {
            return db.values.toList()
        }

        fun preloadData(ctx: Context) {
            db = LessonAssetReader.readCsvFromAssets(ctx, "offline_phrases.csv")
            print(db.size)
        }
    }
}