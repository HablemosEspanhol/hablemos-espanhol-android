package io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.NotificationService
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth.AuthService
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.dataStore
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.state.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

enum class FormField {
    LOGIN,
    PASSWORD
}

class LoginUiViewModel (application: Application): AndroidViewModel(application) {
    private val PROFICIENCY_LEVEL = "proficiency_level"
    private val SCORE = "score"
    private val context = getApplication<Application>()
    private val _uiState = MutableStateFlow(LoginState())
    private val authService = AuthService.instance()
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun onLoginChange(value: String) {
        _uiState.value = _uiState.value.copy(login = value)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun isError(field: FormField): Boolean {

        if (field == FormField.LOGIN){
            val isError = _uiState.value.login.length < 5
            _uiState.value = _uiState.value.copy(formIsValid = !isError)
            return isError
        }
        return false
    }

    suspend fun saveUserName(name: String) {
        val key = stringPreferencesKey("user_name")

        context.dataStore.edit { prefs ->
            prefs[key] = name
        }
    }

    fun getUserName(): Flow<String?> {
        return getUserPreferencies("user_name")
    }

    private fun getUserPreferencies(keyAsString: String): Flow<String?>{
        return context.dataStore.data.map { prefs ->
            prefs[
                stringPreferencesKey(keyAsString)
            ]
        }
    }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = authService.login(_uiState.value.login, _uiState.value.password)
                saveUserName(user.username)
            } catch (e: Exception) {
                NotificationService.notify("Ocorreu um erro ao realizar login: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            saveUserName("")
        }
    }

    fun loadUserStats() {
        viewModelScope.launch {
            getUserPreferencies(PROFICIENCY_LEVEL).collect {
                _uiState.value = _uiState.value.copy(
                    proficiencyLevel = it ?: _uiState.value.proficiencyLevel
                )
            }

            getUserPreferencies(SCORE).collect {
                _uiState.value = _uiState.value.copy(
                    score = if(!it.isNullOrBlank()) it.toInt() else _uiState.value.score
                )
            }
        }
    }

    fun updateProficiencyLevelAndScore(level: String, score: Int) {
        val totalScore = _uiState.value.score + score
        viewModelScope.launch {
            context.dataStore.edit { prefs ->
                prefs[stringPreferencesKey(PROFICIENCY_LEVEL)] = level
                prefs[stringPreferencesKey(SCORE)] = totalScore.toString()
            }
        }

        _uiState.value = _uiState.value.copy(
            proficiencyLevel = level,
            score = totalScore
        )
    }
}

