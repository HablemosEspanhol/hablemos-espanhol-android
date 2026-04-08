package io.github.cadnunsdimir.android.javierchopeklecciones.app.repository

import android.content.Context
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.BaseLessonRestClient
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.LessonAssetReader
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.NotificationService
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.SpreadSheetLessonRestClient
import kotlinx.coroutines.DelicateCoroutinesApi

@Dao
interface LessonRepository {

    @Insert
    suspend fun insert(lesson: Lesson)

    @Insert
    fun insertAll(db: List<Lesson>)
    @Query("SELECT * FROM Lesson where id = :id")
    suspend fun getOne(id: Int): Lesson


    companion object {
//        private var db: Map <Int, Lesson> = mapOf<Int, Lesson>()

        suspend fun getLesson(id: Int): Lesson? = MyDatabase.getLessonRepository().getOne(id)
//        fun getAllLessons(): List<Lesson> {
//            return db.values.toList()
//        }

        @OptIn(DelicateCoroutinesApi::class)
        suspend fun preloadData(ctx: Context) {
            try {
                val service = BaseLessonRestClient.getInstance()
                val repository = MyDatabase.getLessonRepository()
                val db = service.getLessonFromRemote()
                repository.insertAll(db.values.toList())
                NotificationService.notify("Aplicação rodando on-line!")
            } catch (e: Exception) {
                NotificationService.notify("erro ao carregar perguntas remotamente: "+e.message)
//                val db = LessonAssetReader.readCsvFromAssets(ctx, "offline_phrases.csv")
            }
        }
    }


}