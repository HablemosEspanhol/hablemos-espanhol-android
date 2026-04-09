package io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity

import androidx.room.Entity

@Entity
data class MyProgress(val lessonsCompleted: Int, val score: Int)