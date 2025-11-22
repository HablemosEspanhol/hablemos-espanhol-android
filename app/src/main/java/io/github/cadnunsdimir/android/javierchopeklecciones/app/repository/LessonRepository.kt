package io.github.cadnunsdimir.android.javierchopeklecciones.app.repository

import android.content.Context
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Question
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.LessonAssetReader
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.LessonRestClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LessonRepository {
    companion object {
        private var db: Map <Int, Lesson> = mapOf<Int, Lesson>()

        fun getLesson(id: Int): Lesson? = db[id]
        fun getAllLessons(): List<Lesson> {
            return db.values.toList()
        }

        @OptIn(DelicateCoroutinesApi::class)
        fun preloadData(ctx: Context) {
//            try {
//                val service = LessonRestClient()
//                db = service.getLessonFromRemote()
//            } catch (e: Exception) {
//                print("erro ao carregar perguntas remotamente: "+e.message)
//                db = LessonAssetReader.readCsvFromAssets(ctx, "offline_phrases.csv")
//            }
//            print(db.size)
            db = LessonAssetReader.readCsvFromAssets(ctx, "offline_phrases.csv")
            print(db.size)
        }
    }
}