package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LoginUiViewModel

@Composable
fun ProfileScreen(innerPadding: PaddingValues, loginViewModel: LoginUiViewModel) {
    val loggedUser = loginViewModel.uiState.collectAsState().value
    Column {
        Spacer(
            modifier = Modifier
                .padding(innerPadding)
                .height(20.dp)
        )
        Text("Perfil de ${loggedUser.login}",
            fontSize = 30.sp,
            modifier = Modifier.fillMaxWidth())
        Text("Score: ${loggedUser.score}", style = MaterialTheme.typography.displayMedium)
        Text("Lição atual: ${loggedUser.lessonCounter}", style = MaterialTheme.typography.displayMedium)
        Text("Nível de Proficiencia: ${loggedUser.proficiencyLevel}", style = MaterialTheme.typography.displayMedium)

        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Text("Parabéns pelo seu progresso!",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth())
        Button({
            loginViewModel.logout()
        }) {
            Text("Logout")
        }
    }
}