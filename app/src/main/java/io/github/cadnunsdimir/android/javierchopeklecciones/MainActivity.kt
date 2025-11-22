package io.github.cadnunsdimir.android.javierchopeklecciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.cadnunsdimir.android.javierchopeklecciones.app.repository.LessonRepository
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.JavierChopekLeccionesApp
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.theme.JavierChopekLeccionesTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        LessonRepository.preloadData(this@MainActivity)
        setContent {
            JavierChopekLeccionesTheme {
                JavierChopekLeccionesApp()
            }
        }
    }
}