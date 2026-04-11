package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import io.github.cadnunsdimir.android.javierchopeklecciones.BuildConfig
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.AnswerRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.CheckExerciseRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.CheckExerciseResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.ExerciseApiResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.SubmitRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.SubmitResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ExerciseApiService {
    @GET("api/exercises")
    suspend fun getExercises(
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
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseApiService::class.java)
    }
}

class ApiLessonRestClientV2 {
    suspend fun fetchExercises(username: String): List<ExerciseApiResponse> {
        return RetrofitClient.api.getExercises(username)
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