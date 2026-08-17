package io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AuthState {
    AUTHENTICATED,
    UNAUTHENTICATED
}

class TokenManager(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val _authState = MutableStateFlow(
        if (getToken() != null) AuthState.AUTHENTICATED else AuthState.UNAUTHENTICATED
    )

    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun getToken(): String? {
        return sharedPreferences.getString("KEY_AUTH_TOKEN", null)
    }

    fun logout() {
        sharedPreferences.edit { remove("KEY_AUTH_TOKEN") }
        _authState.value = AuthState.UNAUTHENTICATED
    }

    fun onLoginSuccess(token: String) {
        sharedPreferences.edit { putString("KEY_AUTH_TOKEN", token) }
        _authState.value = AuthState.AUTHENTICATED
    }

    companion object {
        private var tokenManager: TokenManager? = null
        fun init(context: Context) : TokenManager{
            tokenManager = if(tokenManager == null) TokenManager(context) else tokenManager
            return instance()
        }

        fun instance(): TokenManager {
            if(tokenManager == null) throw NullPointerException("tokenManager is null")
            return tokenManager as TokenManager
        }
    }
}