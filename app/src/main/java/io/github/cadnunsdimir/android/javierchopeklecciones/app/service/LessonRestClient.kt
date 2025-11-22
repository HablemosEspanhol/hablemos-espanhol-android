package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class LessonRestClient {
//    val client: OkHttpClient = OkHttpClient()
    @Throws(IOException::class)
    fun getCsv(): String {
    try {
        // 1. Cria o objeto URL
        val url = URL(DATABASE_URL)


        // 2. Abre a conexão
        val connection = url.openConnection() as HttpURLConnection


        // 3. Define o método da requisição (padrão é GET)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Accept", "application/json")

        val responseCode = connection.getResponseCode()
        println("Response Code: " + responseCode)

        var responseString: String = ""
        if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
            // 4. Lê a resposta usando um BufferedReader
            val `in` = BufferedReader(InputStreamReader(connection.getInputStream()))
            var inputLine: String?
            val response = StringBuilder()

            while ((`in`.readLine().also { inputLine = it }) != null) {
                response.append(inputLine+"\n")
            }
            `in`.close()

            // 5. Exibe a resposta
            println("Response Body: " + response.toString())
            responseString = response.toString()
        } else {
            println("GET request failed")
        }
        return responseString
    } catch (e: Exception) {
        e.printStackTrace()
        throw  e;
    }
}

    fun getLessonFromRemote(): Map<Int, Lesson>{
        val data = getCsv()
        return LessonAssetReader.transform(data.split("\n"));
    }

    companion object{
        const val DATABASE_URL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vS-JnUSkFLPpQAfCtJ8gS2fwhW8_7M3-jk_-dIVohYQ4hj2UPw4O7xK3S75p9zYHlD49bGlUJzpTmJ2/pub?output=csv"
    }
}