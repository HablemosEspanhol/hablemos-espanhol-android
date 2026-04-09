package io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey
    val id: Int,
    val lessonId: Int,
    val phraseSpanish: String,
    val phrasePortuguese: String
)