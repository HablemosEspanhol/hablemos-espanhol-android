package io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyProgress(
    @PrimaryKey
    val username: String,
    val lessonsCompleted: Int,
    val score: Int
)