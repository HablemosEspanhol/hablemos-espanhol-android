package io.github.cadnunsdimir.android.javierchopeklecciones.app.dto

data class AuthResponse(
    val token: String,
    val expiresIn: Long,
    val user: UserAuthResponse
)
