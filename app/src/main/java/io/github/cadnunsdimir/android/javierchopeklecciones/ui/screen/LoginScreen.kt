package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LoginUiViewModel

@Composable
fun LoginScreen (){
    var loginViewModel = LoginUiViewModel()
    Column {
        Text("Faça o login com seu usuário e senha fornecido pelo professor")
        Spacer(
            modifier = Modifier.height(20.dp)
        )
//        TextField(
//            value = "${ loginViewModel.uiState.value}",
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth(),
//            onValueChange = { loginViewModel.uiState.value = it },
//            label = { Text(text = translate("new_order_label_amount")) },
//            isError = isError(orderUiState, AMOUNT),
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Number
//            )
//        )
    }
}