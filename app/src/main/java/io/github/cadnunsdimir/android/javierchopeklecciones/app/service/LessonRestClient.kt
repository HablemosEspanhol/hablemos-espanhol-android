package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson

class LessonRestClient {
    fun getLessonFromRemote(): List<Lesson>{
        TODO("recuperar dados do csv do google docs e converter para a entidade correspondente")
    }

    companion object{
        const val DATABASE_URL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vS-JnUSkFLPpQAfCtJ8gS2fwhW8_7M3-jk_-dIVohYQ4hj2UPw4O7xK3S75p9zYHlD49bGlUJzpTmJ2/pub?output=csv"
    }
}