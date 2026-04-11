package io.github.cadnunsdimir.android.javierchopeklecciones.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen.HomeScreen
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen.LessonScreen
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen.LessonScreenV2
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen.LoginScreen
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen.ProfileScreen
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.LoginUiViewModel

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
    val showLogged: Boolean? = null
) {
    HOME("Inicio", Icons.Default.Home),
    EXERCISES_V1("Ejercícios (offline)", Icons.Default.Create, false),
    EXERCISES_V2("Ejercícios", Icons.Default.Create, true),
    PROFILE("Perfil", Icons.Default.AccountBox, true),
    LOGIN("Login", Icons.Default.AccountBox, false),
}


@Composable
fun JavierChopekLeccionesApp(loginViewModel: LoginUiViewModel = viewModel()) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val isLogged = loginViewModel.isLogged().collectAsState(false)
    loginViewModel.loadUserStats()

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                val showMenu = it.showLogged == null ||
                        it.showLogged == isLogged.value

                if (showMenu){
                    item(
                        icon = {
                            Icon(
                                it.icon,
                                contentDescription = it.label
                            )
                        },
                        label = { Text(it.label) },
                        selected = it == currentDestination,
                        onClick = { currentDestination = it }
                    )
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
                .padding(5.dp)
                .padding(top= 40.dp)

        ) { innerPadding ->
            when(currentDestination) {
                AppDestinations.HOME -> HomeScreen(innerPadding) {
                    currentDestination = if(isLogged.value) AppDestinations.EXERCISES_V2 else AppDestinations.EXERCISES_V1
                }
                AppDestinations.EXERCISES_V1 -> LessonScreen()
                AppDestinations.EXERCISES_V2 -> LessonScreenV2(loginViewModel = loginViewModel)
                AppDestinations.PROFILE -> ProfileScreen(innerPadding, loginViewModel) {
                    currentDestination = AppDestinations.HOME
                }
                AppDestinations.LOGIN -> LoginScreen(innerPadding, loginViewModel){
                    currentDestination = AppDestinations.HOME
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    JavierChopekLeccionesApp()
}