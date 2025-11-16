package io.github.cadnunsdimir.android.javierchopeklecciones.ui.stt

import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.SpeechRecognitionListener
import java.text.Normalizer


private fun getNewPhrase(expectedText: String? = null): String {
    val words = arrayOf("Saludos", "Buenos Días", "Hola");
    val indexNextWord = if(expectedText == null) 0 else words.indexOf(expectedText) + 1;
    return if(indexNextWord < words.size) words[indexNextWord] else words[0]
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OfflineSpeechToTextScreen() {
    val context = LocalContext.current
    var recognizedText by remember { mutableStateOf("Aguardando...") }
    var buttonText by remember { mutableStateOf("Gravar Pronúncia") }
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    var expectedText by remember { mutableStateOf( getNewPhrase()) }
    var statusWordGuesser by remember { mutableStateOf(StatusWordGuesser.NEW) }

    val recordAudioPermissionState = rememberPermissionState(
        android.Manifest.permission.RECORD_AUDIO
    )

    val startRecognition = {
        val listener = SpeechRecognitionListener(
            onEndSpeech = {
                buttonText = "Gravar Pronúncia"
            },
            onResult = { result ->
                recognizedText = result
                val haveGuessed = checkIfGuessed(recognizedText, expectedText)
                statusWordGuesser = if(haveGuessed)  StatusWordGuesser.DONE else StatusWordGuesser.WRONG
                speechRecognizer.destroy()
            },
            onErrorListener = { error ->
                recognizedText = when (error) {
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT, SpeechRecognizer.ERROR_NETWORK ->
                        "Ops! Você está offline e não foi possível usar o idioma do seu dispositivo"
                    SpeechRecognizer.ERROR_NO_MATCH ->
                        "Não entendi o que disse. Pode repetir?"
                    else -> "Ops! Algo deu errado"
                }
                print(error)
                buttonText = "Gravar Pronúncia"
                speechRecognizer.destroy()
            }
        )
        speechRecognizer.setRecognitionListener(listener)

        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
//            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
            putExtra("android.speech.extra.PREFER_OFFLINE", true) // Tenta forçar offline
        }

        speechRecognizer.startListening(recognizerIntent)

        buttonText = "Ouvindo..."
    }

    Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = {
            if (recordAudioPermissionState.status.isGranted) {
                if (SpeechRecognizer.isRecognitionAvailable(context)) {
                    startRecognition()
                } else {
                    recognizedText = "Reconhecimento de fala indisponível."
                }
            } else {
                recordAudioPermissionState.launchPermissionRequest()
            }
        }) {
            Text(buttonText)
        }

        Spacer(Modifier.height(16.dp))
        Text(recognizedText, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        WordGuesserComponent(statusWordGuesser, expectedText,
            onGuess = {
                expectedText = getNewPhrase(it)
                statusWordGuesser = StatusWordGuesser.NEW
            }
        )
    }
}
fun normalize(text:String): String {
    return Normalizer
        .normalize(text, Normalizer.Form.NFD)
        .replace("\\p{Mn}+".toRegex(), "")
        .lowercase()
}
fun checkIfGuessed(recognizedText: String, expectedText: String) : Boolean{
    val recognizedTextNormalized = normalize(recognizedText)
    val expectedTextNormalized = normalize(expectedText)
    return recognizedTextNormalized.contains(expectedTextNormalized)
}

@Composable
fun WordGuesserComponent(status: StatusWordGuesser, expectedText: String, onGuess: (String) -> Unit) {
    val color = mapOf(
        StatusWordGuesser.WRONG to Color.Red,
        StatusWordGuesser.DONE to Color.Green,
    )[status]?: Color.DarkGray
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(expectedText,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = color
        )
        if(status == StatusWordGuesser.DONE){
            Button(
                onClick = {
                    onGuess(expectedText)
                }
            ) {
                Text("Próxima Palavra")
            }
        }
    }
}

enum class StatusWordGuesser {
    NEW,
    WRONG,
    DONE
}