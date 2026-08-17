package io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth

import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.AuthRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRestClient {
    @POST("api/auth")
    suspend fun postLogin(
        @Body request: AuthRequest
    ): AuthResponse
}