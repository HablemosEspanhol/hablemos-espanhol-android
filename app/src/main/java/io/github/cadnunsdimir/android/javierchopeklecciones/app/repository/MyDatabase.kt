package io.github.cadnunsdimir.android.javierchopeklecciones.app.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.MyProgress
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Question



@Database(entities = [Lesson::class, Question::class, MyProgress::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun lessonRepository(): LessonRepository
    abstract fun progressRepository(): ProgressRepository

    companion object {
        private lateinit var _db: MyDatabase
        fun initDb(db: MyDatabase) {
            _db = db
        }

        fun getLessonRepository(): LessonRepository {
            return _db.lessonRepository()
        }
    }
}