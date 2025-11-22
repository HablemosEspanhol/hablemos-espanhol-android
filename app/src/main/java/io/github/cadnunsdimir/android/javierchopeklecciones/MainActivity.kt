package io.github.cadnunsdimir.android.javierchopeklecciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import io.github.cadnunsdimir.android.javierchopeklecciones.app.repository.LessonRepository
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.JavierChopekLeccionesApp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.theme.JavierChopekLeccionesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                LessonRepository.preloadData(this@MainActivity)
            }
        }
        setContent {
            JavierChopekLeccionesTheme {
                JavierChopekLeccionesApp()
            }
        }
    }
}