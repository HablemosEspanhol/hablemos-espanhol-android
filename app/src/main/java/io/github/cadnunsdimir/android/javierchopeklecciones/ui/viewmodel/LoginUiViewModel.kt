package io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.github.cadnunsdimir.android.javierchopeklecciones.app.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class LoginUiViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()
}