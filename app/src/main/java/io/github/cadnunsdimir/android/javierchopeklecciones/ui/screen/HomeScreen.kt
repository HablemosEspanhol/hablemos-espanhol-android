package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
    ){
        Text(
            text = "Professor de Espanhol, Javier Chopek",
            modifier = modifier.fillMaxWidth(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.padding(10.dp))
        Text(
            text = "Seja Bem vindo ao aplicativo exclusivo para os meus alunos, onde poderei passar exercícios em espanhol para vocês",
            modifier = modifier
        )
    }
}