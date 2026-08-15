package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import io.github.cadnunsdimir.android.javierchopeklecciones.BuildConfig
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.AnswerRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.CheckExerciseRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.CheckExerciseResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.ExerciseApiResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.SubmitRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.SubmitResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ExerciseApiService {
    @GET("api/exercises")
    suspend fun getExercises(
        @Query("username") username: String
    ): List<ExerciseApiResponse>

    @GET("api/exercises/v2")
    suspend fun getExercisesV2(
        @Query("username") username: String
    ): List<ExerciseApiResponse>

    @POST("api/exercises/check")
    suspend fun checkExercise(
        @Body request: CheckExerciseRequest
    ): CheckExerciseResponse

    @POST("api/exercises/submit")
    suspend fun submitAnswers(
        @Body request: SubmitRequest
    ): SubmitResponse
}

object RetrofitClient {
    val api: ExerciseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    // Tempo para estabelecer a conexão inicial TCP/TLS
                    .connectTimeout(15, TimeUnit.SECONDS)

                    // Tempo limite entre pacotes recebidos (O MAIS IMPORTANTE PARA LLM)
                    // O ideal é 45s a 60s para dar margem a oscilações da API do Gemini sem falhar a requisição do usuário
                    .readTimeout(60, TimeUnit.SECONDS)

                    // Tempo máximo para envio dos dados da requisição
                    .writeTimeout(15, TimeUnit.SECONDS)

                    // (Opcional) Teto absoluto para toda a transação HTTP
                    .callTimeout(90, TimeUnit.SECONDS)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseApiService::class.java)
    }
}

class ApiLessonRestClientV2 {
    suspend fun fetchExercises(username: String): List<ExerciseApiResponse> {
        return RetrofitClient.api.getExercisesV2(username)
    }
    suspend fun checkAnswer(
        username: String,
        exerciseId: String,
        answer: String
    ): CheckExerciseResponse {

        val request = CheckExerciseRequest(
            username = username,
            answer = AnswerRequest(
                exerciseId = exerciseId,
                answer = answer
            )
        )

        return RetrofitClient.api.checkExercise(request)
    }

    suspend fun submitAnswers(
        username: String,
        answers: List<AnswerRequest>
    ): SubmitResponse {

        val request = SubmitRequest(
            username = username,
            answers = answers
        )

        return RetrofitClient.api.submitAnswers(request)
    }
}