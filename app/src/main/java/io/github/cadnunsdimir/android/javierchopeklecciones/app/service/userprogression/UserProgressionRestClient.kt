package io.github.cadnunsdimir.android.javierchopeklecciones.app.service.userprogression

import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.ProgressApiResponse
import retrofit2.Response
import retrofit2.http.GET

class UserProgressionRestClient {

}

interface UserProgressionRestContract{
    @GET("api/progress")
    suspend fun getProgress(): Response<ProgressApiResponse>
}
