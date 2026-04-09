package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.FormField
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LoginUiViewModel

@Composable
fun LoginScreen(
    innerPadding: PaddingValues,
    loginViewModel: LoginUiViewModel,
    onLoginSuccess: ()-> Unit) {
    val focusManager = LocalFocusManager.current
    val uiState = loginViewModel.uiState.collectAsStateWithLifecycle()
    val formIsValid = loginViewModel.isFormValid()

    Column (Modifier.padding(innerPadding)) {
        Text("Faça o login com seu usuário e senha fornecido pelo professor")
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        OutlinedTextField(
            value = uiState.value.login,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { loginViewModel.onLoginChange(it) },
            label = { Text("Login") },
            isError = loginViewModel.isError( FormField.LOGIN),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        OutlinedTextField(
            value = uiState.value.password,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { loginViewModel.onPasswordChange(it) },
            label = { Text("Senha") },
            isError = loginViewModel.isError( FormField.PASSWORD),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Button(onClick = {
            if(loginViewModel.login()){
                onLoginSuccess()
            }
        },
            enabled = formIsValid
        ) {
            Text("Entrar")
        }
    }
}