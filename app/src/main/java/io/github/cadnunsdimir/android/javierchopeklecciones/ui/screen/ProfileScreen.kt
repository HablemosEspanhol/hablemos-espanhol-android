package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LoginUiViewModel

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    loginViewModel: LoginUiViewModel,
    onLogoutSuccess: () -> Unit
) {

    val user = loginViewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 👤 HEADER
        Text(
            text = "Perfil",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = user.login,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                ProfileItem(label = "Score", value = user.score.toString())
                Spacer(modifier = Modifier.height(12.dp))

                ProfileItem(label = "Lição atual", value = user.lessonCounter.toString())
                Spacer(modifier = Modifier.height(12.dp))

                ProfileItem(label = "Nível", value = user.proficiencyLevel)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Continue praticando durante a semana para melhorar seu desempenho.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = {
                loginViewModel.logout()
                onLogoutSuccess()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sair")
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium
        )
    }
}