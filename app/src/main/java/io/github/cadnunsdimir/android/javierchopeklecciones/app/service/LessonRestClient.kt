package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


class LessonRestClient {
//    val client: OkHttpClient = OkHttpClient()
//    @Throws(IOException::class)
//    fun getCsv(): String {
//        val request: Request = Request.Builder()
//            .url(DATABASE_URL)
//            .build()
//
//        return client.newCall(request).execute().use { response ->
//            if (!response.isSuccessful) {
//                throw IOException("Código de resposta inesperado: $response")
//            }
//
//            response.body.string()
//        }
//    }
//
//    fun getLessonFromRemote(): Map<Int, Lesson>{
//        val data = getCsv()
//        return LessonAssetReader.transform(data.split("\n"));
//    }

    companion object{
        const val DATABASE_URL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vS-JnUSkFLPpQAfCtJ8gS2fwhW8_7M3-jk_-dIVohYQ4hj2UPw4O7xK3S75p9zYHlD49bGlUJzpTmJ2/pub?output=csv"
    }
}