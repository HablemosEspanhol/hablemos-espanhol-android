package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FinishLesson(
    message: String? = null,
    nextLesson: Int = 1,
    score: Int, onNextLesson: (Int) -> Unit) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card (
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp)
        ){
            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Parabéns!!!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text("Você concluiu 100% da sua lição! $message")
                Text("Score: $score")
                Button({
                    onNextLesson(nextLesson)
                }){
                    Text("Iniciar lição $nextLesson")
                }
            }
        }
    }

}