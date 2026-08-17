package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import io.github.cadnunsdimir.android.javierchopeklecciones.R
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth.AuthState
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth.TokenManager

@Composable
fun HomeScreen(
    tokenManager: TokenManager,
    ctx: Context = LocalContext.current,
    onStartLesson: () -> Unit = {}
) {
    val authState = tokenManager.authState.collectAsState()
    val startLessonButtonText = if(authState.value == AuthState.AUTHENTICATED) "Iniciar lição" else "Faça Login"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        Text(
            text = getString(ctx, R.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.splash_screen),
            contentDescription = "imagem app",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = getString(ctx, R.string.home_text),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onStartLesson,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(startLessonButtonText)
        }
    }
}