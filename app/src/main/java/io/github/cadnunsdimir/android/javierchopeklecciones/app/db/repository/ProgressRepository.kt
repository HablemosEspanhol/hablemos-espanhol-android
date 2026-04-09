package io.github.cadnunsdimir.android.javierchopeklecciones.app.db.repository

import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.MyProgress

class ProgressRepository {
    companion object {
        private var _score: Int = 0
        private var _lessonId: Int = 0

        fun saveCompletedLesson(lessonId: Int, score: Int) {
            _lessonId = lessonId
            _score += score
        }

        fun getNextLesson(): Int {
            return _lessonId + 1
        }

        fun getStatistics(): MyProgress {
            return MyProgress(_lessonId, _score)
        }
    }
}