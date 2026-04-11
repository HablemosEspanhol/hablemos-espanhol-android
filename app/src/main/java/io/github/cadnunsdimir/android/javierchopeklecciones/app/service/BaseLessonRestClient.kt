package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.LessonWithQuestions
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

abstract class BaseLessonRestClient {
    abstract fun getLessonFromRemote(): Map<Int, LessonWithQuestions>

    @Throws(IOException::class)
    fun get(urlString: String): String {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/json")

            val responseCode = connection.getResponseCode()
            println("Response Code: $responseCode")

            var responseString = ""
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val `in` = BufferedReader(InputStreamReader(connection.getInputStream()))
                var inputLine: String?
                val response = StringBuilder()

                while ((`in`.readLine().also { inputLine = it }) != null) {
                    response.append(inputLine + "\n")
                }
                `in`.close()
                println("Response Body: $response")
                responseString = response.toString()
            } else {
                println("GET request failed")
            }
            return responseString
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    companion object {
        fun getInstance(): BaseLessonRestClient {
//            return ApiLessonRestRestClient()
            return SpreadSheetLessonRestClient()
        }
    }
}