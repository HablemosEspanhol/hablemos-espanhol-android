package io.github.cadnunsdimir.android.javierchopeklecciones.app.service.lesson

import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.AnswerRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.CheckExerciseRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.CheckExerciseResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.ExerciseApiResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.SubmitRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.SubmitResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.RetrofitClient
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ExerciseApiService {
    @GET("api/exercises")
    suspend fun getExercises(): List<ExerciseApiResponse>

    @GET("api/exercises/v2")
    suspend fun getExercisesV2(): List<ExerciseApiResponse>

    @POST("api/exercises/check")
    suspend fun checkExercise(
        @Body request: CheckExerciseRequest
    ): CheckExerciseResponse

    @POST("api/exercises/submit")
    suspend fun submitAnswers(
        @Body request: SubmitRequest
    ): SubmitResponse
}

class ApiLessonRestClientV2 {
    suspend fun fetchExercises(): List<ExerciseApiResponse> {
        return RetrofitClient.exerciseApi.getExercisesV2()
    }
    suspend fun checkAnswer(
        exerciseId: String,
        answer: String
    ): CheckExerciseResponse {

        val request = CheckExerciseRequest(
            answer = AnswerRequest(
                exerciseId = exerciseId,
                answer = answer
            )
        )

        return RetrofitClient.exerciseApi.checkExercise(request)
    }

    suspend fun submitAnswers(
        answers: List<AnswerRequest>
    ): SubmitResponse {

        val request = SubmitRequest(
            answers = answers
        )

        return RetrofitClient.exerciseApi.submitAnswers(request)
    }
}