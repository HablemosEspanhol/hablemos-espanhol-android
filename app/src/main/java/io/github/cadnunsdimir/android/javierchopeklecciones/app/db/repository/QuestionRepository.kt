package io.github.cadnunsdimir.android.javierchopeklecciones.app.db.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.Question

@Dao
interface QuestionRepository {
    @Insert
    fun insertAll(db: List<Question>)

    @Query("DELETE FROM Question")
    fun deleteAll()

    @Query("select count(1) from Question")
    fun count(): Int
}