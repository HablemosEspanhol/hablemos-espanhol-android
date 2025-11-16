package io.github.cadnunsdimir.android.javierchopeklecciones.ui.stt

import android.Manifest
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.github.cadnunsdimir.android.javierchopeklecciones.app.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.repository.LessonRepository
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.SpeechRecognitionListener
import java.text.Normalizer


const val WAITING = "Aguardando..."
const val RECORD = "Gravar Pronúncia"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OfflineSpeechToTextScreen() {

    val context = LocalContext.current
    val lesson = LessonRepository.getLesson(1) as Lesson
    var recognizedText by remember { mutableStateOf(WAITING) }
    var buttonText by remember { mutableStateOf(RECORD) }
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    var expectedText by remember { mutableStateOf( lesson.getNewPhrase().phraseSpanish) }
    var statusWordGuesser by remember { mutableStateOf(StatusWordGuesser.NEW) }
    var percentualProgress by remember { mutableFloatStateOf(0f) }
    var progress by remember { mutableIntStateOf(0) }

    val recordAudioPermissionState = rememberPermissionState(
        Manifest.permission.RECORD_AUDIO
    )

    val startRecognition = {
        val listener = SpeechRecognitionListener(
            onEndSpeech = {
                buttonText = RECORD
            },
            onResult = { result ->
                recognizedText = result
                val haveGuessed = checkIfGuessed(recognizedText, expectedText)
                statusWordGuesser = if(haveGuessed)  StatusWordGuesser.DONE else StatusWordGuesser.WRONG
                if(haveGuessed) {
                    progress += 1
                    percentualProgress = progress.toFloat() / lesson.questions.size
                }
                speechRecognizer.destroy()
            },
            onErrorListener = { error ->
                recognizedText = when (error) {
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT, SpeechRecognizer.ERROR_NETWORK ->
                        "Ops! Você está offline e não foi possível usar o idioma do seu dispositivo"
                    SpeechRecognizer.ERROR_NO_MATCH ->
                        "Não entendi o que disse. Pode repetir?"
                    else -> "Ops! Algo deu errado. codigo de erro: $error"
                }
                print(error)
                buttonText = RECORD
                speechRecognizer.destroy()
            }
        )
        speechRecognizer.setRecognitionListener(listener)

        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
//            putExtra("android.speech.extra.PREFER_OFFLINE", true) // Tenta forçar offline
        }

        speechRecognizer.startListening(recognizerIntent)
        buttonText = "Ouvindo ..."
    }

    Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        LessonProgressBar(percentualProgress)
        Spacer(Modifier.height(16.dp))
        WordGuesserComponent(statusWordGuesser, expectedText,
            onProgress = {
                expectedText = lesson.getNewPhrase(it).phraseSpanish
                statusWordGuesser = StatusWordGuesser.NEW
                recognizedText = WAITING
            }
        )
        Spacer(Modifier.height(16.dp))
        Text(recognizedText, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
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
    }
}

@Composable
fun LessonProgressBar(percentualProgress: Float) {

    LinearProgressIndicator(
        progress = { percentualProgress }, // Ex: 0.75f para 75%
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
        color = Color.Blue,
        trackColor = Color.LightGray,
        strokeCap = StrokeCap.Round,
        gapSize = (-10).dp,
    )
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
    val totalWords = expectedTextNormalized.split(" ").size
    var guessedWords = 0f

    for (word in recognizedTextNormalized.split(" ")){
        if(expectedTextNormalized.contains(word))
            ++guessedWords
    }
    val percentage = guessedWords / totalWords
    return percentage > 0.6f
}

@Composable
fun WordGuesserComponent(status: StatusWordGuesser, expectedText: String, onProgress: (String) -> Unit) {
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
            Row (
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        onProgress(expectedText)
                    }
                ) {
                    Text("Próxima Palavra")
                }
            }
        }
    }
}

enum class StatusWordGuesser {
    NEW,
    WRONG,
    DONE
}