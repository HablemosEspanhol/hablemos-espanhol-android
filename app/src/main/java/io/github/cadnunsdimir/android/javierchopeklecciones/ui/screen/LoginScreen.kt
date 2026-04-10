package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.FormField
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LoginUiViewModel

@Composable
fun LoginScreen(
    innerPadding: PaddingValues,
    loginViewModel: LoginUiViewModel,
    onLoginSuccess: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val uiState = loginViewModel.uiState.collectAsStateWithLifecycle()
    val formIsValid = loginViewModel.isFormValid()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "Acesso do aluno",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Use o usuário e senha fornecidos pelo professor.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 👤 LOGIN
                OutlinedTextField(
                    value = uiState.value.login,
                    onValueChange = { loginViewModel.onLoginChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Usuário") },
                    isError = loginViewModel.isError(FormField.LOGIN),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.value.password,
                    onValueChange = { loginViewModel.onPasswordChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Senha") },
                    isError = loginViewModel.isError(FormField.PASSWORD),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            if (formIsValid && loginViewModel.login()) {
                                onLoginSuccess()
                            }
                        }
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (loginViewModel.login()) {
                            onLoginSuccess()
                        }
                    },
                    enabled = formIsValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Entrar")
                }
            }
        }
    }
}