package io.github.cadnunsdimir.android.javierchopeklecciones

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.repository.DatabaseProvider
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.NotificationService
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.RetrofitClient
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth.TokenManager
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.JavierChopekLeccionesApp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.theme.JavierChopekLeccionesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val tokenManager: TokenManager = TokenManager.init(this@MainActivity)

        NotificationService.subscribe {
            showErrorToUserOnMainThread(it)
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                try{
                    DatabaseProvider.preloadData(this@MainActivity)

                    RetrofitClient.init(tokenManager)
                } catch (e: Exception) {
                    showErrorToUserOnMainThread(e.message?:"Erro desconhecido")
                }
            }
        }

        setContent {
            JavierChopekLeccionesTheme {
                JavierChopekLeccionesApp(tokenManager = tokenManager)
            }
        }
    }

    private fun showErrorToUserOnMainThread(message: String) {
        lifecycleScope.launch {
            withContext(Dispatchers.Main){
                Toast.makeText(
                    this@MainActivity,
                    message,
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}