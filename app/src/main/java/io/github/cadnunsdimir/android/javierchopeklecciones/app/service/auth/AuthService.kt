package io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth

import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.AuthRequest
import io.github.cadnunsdimir.android.javierchopeklecciones.app.dto.UserAuthResponse
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.RetrofitClient

class AuthService(val tokenManager: TokenManager)    {
    suspend fun login(username: String, password: String): UserAuthResponse {
        val data = RetrofitClient.authApi.postLogin(AuthRequest(username, password))
        tokenManager.onLoginSuccess(data.token)
        return data.user
    }

    companion object {
        fun instance(): AuthService {
            return AuthService(TokenManager.instance())
        }
    }
}
