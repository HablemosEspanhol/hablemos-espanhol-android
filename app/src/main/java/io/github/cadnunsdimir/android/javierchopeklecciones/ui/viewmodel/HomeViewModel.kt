package io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.RetrofitClient
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.userprogression.UserProgressRepository
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.state.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: UserProgressRepository = UserProgressRepository(RetrofitClient.userProgressionApi)
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _messageState = MutableStateFlow<String?>(null)
    val messageState: StateFlow<String?> = _messageState.asStateFlow()

    init {
//        loadLoggedInPreviewState()
        loadUserProgress()
    }

//    fun loadLoggedInPreviewState() {
//        _uiState.value = HomeUiState.LoggedIn(
//            userProgress = UserProgress(
//                currentLevel = CefrLevel.A2,
//                totalStudyDays = 84,
//                streakCount = 12,
//                completedLessonsInCurrentLevel = 14,
//                totalLessonsInCurrentLevel = 24,
//                estimatedB1CompletionDate = "Outubro/2026"
//            )
//        )
//    }

    fun loadUserProgress() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            repository.fetchUserProgress()
                .onSuccess { progress ->
                    _uiState.value = HomeUiState.LoggedIn(progress)
                }
                .onFailure { error ->
                    // Tratar erro no estado
                    _messageState.value = "[Error] ${error.message}"
                }
        }
    }

    fun showLoggedOut() {
        _uiState.value = HomeUiState.LoggedOut
    }
}
