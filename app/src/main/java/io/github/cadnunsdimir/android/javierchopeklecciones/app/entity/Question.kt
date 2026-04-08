package io.github.cadnunsdimir.android.javierchopeklecciones.app.entity

import androidx.room.Entity

@Entity
data class Question(val id: Int, val phraseSpanish: String, val phrasePortuguese: String)