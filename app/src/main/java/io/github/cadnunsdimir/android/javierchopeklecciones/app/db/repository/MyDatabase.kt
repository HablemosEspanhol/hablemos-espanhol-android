package io.github.cadnunsdimir.android.javierchopeklecciones.app.db.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.MyProgress
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.Question
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.lesson.BaseLessonRestClient
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.NotificationService


@Database(entities = [Lesson::class, Question::class, MyProgress::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun lessonRepository(): LessonRepository
    abstract fun questionRepository(): QuestionRepository
    abstract fun progressRepository(): ProgressRepository
}

object DatabaseProvider {
    @Volatile
    private var INSTANCE: MyDatabase? = null

    fun getDatabase(context: Context): MyDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                MyDatabase::class.java,
                "my_database"
            ).build()

            INSTANCE = instance
            instance
        }
    }

    fun preloadData(ctx: Context) {
        try {

            val db = getDatabase(ctx)
            val service = BaseLessonRestClient.getInstance()
            val repository = db.lessonRepository()
            val questionRepository = db.questionRepository()
            if (repository.count() == 0 || questionRepository.count() == 0) {
                val data = service.getLessonFromRemote()

                repository.deleteAll()
                questionRepository.deleteAll()

                val lessons = data.values.map { it.lesson }
                repository.insertAll(lessons)

                val questions = data.values.flatMap { it.questions }
                    .mapIndexed { index, question -> Question (
                        index + 1,
                        lessonId = question.lessonId,
                        phraseSpanish = question.phraseSpanish,
                        phrasePortuguese = question.phrasePortuguese
                    ) }
                questionRepository.insertAll(questions)
            }
        } catch (e: Exception) {
            NotificationService.notify("erro ao carregar perguntas remotamente: "+e.message)
        }
    }
}