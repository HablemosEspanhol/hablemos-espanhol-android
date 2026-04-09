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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.repository.ProgressRepository
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LoginUiViewModel

@Composable
fun ProfileScreen(innerPadding: PaddingValues, loginViewModel: LoginUiViewModel) {
    var estatisticas = ProgressRepository.getStatistics()
    Column {
        Spacer(
            modifier = Modifier
                .padding(innerPadding)
                .height(20.dp)
        )
        Text("Perfil",
            fontSize = 30.sp,
            modifier = Modifier.fillMaxWidth())
        Text("Score: ${estatisticas.score}", style = MaterialTheme.typography.displayMedium)
        Text("Lição atual: ${estatisticas.score}", style = MaterialTheme.typography.displayMedium)
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