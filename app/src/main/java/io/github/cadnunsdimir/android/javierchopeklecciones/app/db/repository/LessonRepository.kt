package io.github.cadnunsdimir.android.javierchopeklecciones.app.db.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.Lesson

@Dao
interface LessonRepository {
    @Insert
    suspend fun insert(lesson: Lesson)
    @Insert
    fun insertAll(db: List<Lesson>)
    @Query("SELECT * FROM Lesson where id = :id")
    suspend fun getOne(id: Int): Lesson

}