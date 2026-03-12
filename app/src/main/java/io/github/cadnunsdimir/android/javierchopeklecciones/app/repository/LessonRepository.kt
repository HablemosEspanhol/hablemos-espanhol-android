package io.github.cadnunsdimir.android.javierchopeklecciones.app.repository

import android.content.Context
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.BaseLessonRestClient
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.LessonAssetReader
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.NotificationService
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.SpreadSheetLessonRestClient
import kotlinx.coroutines.DelicateCoroutinesApi

class LessonRepository() {

    companion object {
        private var db: Map <Int, Lesson> = mapOf<Int, Lesson>()

        fun getLesson(id: Int): Lesson? = db[id]
        fun getAllLessons(): List<Lesson> {
            return db.values.toList()
        }

        @OptIn(DelicateCoroutinesApi::class)
        fun preloadData(ctx: Context) {
            try {
                val service = BaseLessonRestClient.getInstance();
                db = service.getLessonFromRemote()
                NotificationService.notify("Aplicação rodando on-line!")
            } catch (e: Exception) {
                NotificationService.notify("erro ao carregar perguntas remotamente: "+e.message)
                db = LessonAssetReader.readCsvFromAssets(ctx, "offline_phrases.csv")
            }
        }
    }
}