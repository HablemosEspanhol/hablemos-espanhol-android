package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.LessonWithQuestions
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.lesson.BaseLessonRestClient
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.lesson.LessonAssetReader


class SpreadSheetLessonRestClient : BaseLessonRestClient() {
    override fun getLessonFromRemote(): Map<Int, LessonWithQuestions>{
        val data = get(DATABASE_URL)
        return LessonAssetReader.transform(data.split("\n"))
    }

    companion object{
        const val DATABASE_URL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vS-JnUSkFLPpQAfCtJ8gS2fwhW8_7M3-jk_-dIVohYQ4hj2UPw4O7xK3S75p9zYHlD49bGlUJzpTmJ2/pub?output=csv"
    }
}