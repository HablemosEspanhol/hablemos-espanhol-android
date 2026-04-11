package io.github.cadnunsdimir.android.javierchopeklecciones.app.db.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.LessonWithQuestions

@Dao
interface LessonRepository {
    @Insert
    fun insertAll(db: List<Lesson>)
    @Transaction
    @Query("SELECT * FROM Lesson where id = :id")
    suspend fun getOne(id: Int): LessonWithQuestions

    @Query("DELETE FROM Lesson")
    fun deleteAll()

    @Query("select count(1) from Lesson")
    fun count(): Int
}