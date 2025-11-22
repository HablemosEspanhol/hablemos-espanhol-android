package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import io.github.cadnunsdimir.android.javierchopeklecciones.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier, ctx: Context = LocalContext.current) {
    Column (
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
    ){
        Text(
            text = getString(ctx, R.string.app_name),
            modifier = modifier.fillMaxWidth(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.padding(10.dp))
        Image(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            painter = painterResource(R.drawable.splash_screen),
            contentDescription = "imagem app"
        )
        Spacer(Modifier.padding(10.dp))
        Text(
            text =  getString(ctx, R.string.home_text),
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomeScreen(ctx = LocalContext.current)
}