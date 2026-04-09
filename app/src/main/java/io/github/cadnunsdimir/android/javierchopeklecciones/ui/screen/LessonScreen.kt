package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import android.Manifest
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.entity.Lesson
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.repository.DatabaseProvider
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.repository.LessonRepository
import io.github.cadnunsdimir.android.javierchopeklecciones.app.db.repository.ProgressRepository
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.SpeechRecognitionListener
import kotlinx.coroutines.runBlocking
import java.text.Normalizer

const val WAITING = "Aguardando..."
const val RECORD = "Gravar Pronúncia"


@Composable
fun LessonScreen() {
    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val repository = db.lessonRepository()
    val lessonEntity: Lesson
    runBlocking{
        lessonEntity = repository.getOne(ProgressRepository.getNextLesson())
    }
    var lessonScore = 20
    val penalty = 1
    lessonEntity.randomizeQuestions()
    var lesson by remember { mutableStateOf(lessonEntity) }
    var recognizedText by remember { mutableStateOf(WAITING) }
    var buttonText by remember { mutableStateOf(RECORD) }
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    var expectedText by remember { mutableStateOf( lesson.getNewPhrase().phraseSpanish) }
    var statusWordGuesser by remember { mutableStateOf(StatusWordGuesser.NEW) }
    var percentualProgress by remember { mutableFloatStateOf(0f) }
    var progress by remember { mutableIntStateOf(0) }

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
                } else if(lessonScore > 10){
                    lessonScore -= penalty
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

    if(progress == lesson.questions.size){
        FinishLesson(lesson.id + 1, lessonScore,
            onNextLesson = {
                ProgressRepository.saveCompletedLesson(lesson.id, lessonScore)
                val lessonEntity: Lesson?
                runBlocking{
                    lessonEntity = repository.getOne(ProgressRepository.getNextLesson())
                }
                if(lessonEntity !== null) {
                    lessonEntity.randomizeQuestions()
                    lesson = lessonEntity
                    progress = 0
                    lessonScore = 20
                    percentualProgress = 0f
                }
            }
        )
        return
    }

    Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(20.dp))
        LessonProgressBar(percentualProgress)
        Spacer(Modifier.height(20.dp))
        Text("Lição ${lesson.id}")
        Spacer(Modifier.height(10.dp))
        Text("Pronuncie corretamente o texto abaixo em Espanhol:")
        Spacer(Modifier.height(20.dp))
        WordGuesserComponent(statusWordGuesser, expectedText)
        Spacer(Modifier.height(20.dp))
        Text(recognizedText, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))

        if(statusWordGuesser != StatusWordGuesser.DONE) {
            RecordAudioButton(buttonText,
                { startRecognition() },
                {recognizedText = it})
        } else{
            NextQuestionButton(onProgress = {
                expectedText = lesson.getNewPhrase(expectedText).phraseSpanish
                statusWordGuesser = StatusWordGuesser.NEW
                recognizedText = WAITING
            })
        }

    }
}

@Composable
fun NextQuestionButton(onProgress: () -> Unit) {
    Row (
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                onProgress()
            }
        ) {
            Text("Próxima Palavra")
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RecordAudioButton(buttonText: String, onStartRecognition: ()-> Unit, onError: (text: String)-> Unit) {
    val recordAudioPermissionState = rememberPermissionState(
        Manifest.permission.RECORD_AUDIO
    )
    val ctx = LocalContext.current
    Button(onClick = {
        if (recordAudioPermissionState.status.isGranted) {
            if (SpeechRecognizer.isRecognitionAvailable(ctx)) {
                onStartRecognition()
            } else {
                onError("Reconhecimento de fala indisponível.")
            }
        } else {
            recordAudioPermissionState.launchPermissionRequest()
        }
    }) {
        Text(buttonText)
    }
}
@Composable
fun FinishLesson(nextLesson: Int, score: Int, onNextLesson: (Int) -> Unit) {
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
                Text("Você concluiu 100% da sua lição!")
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
    return percentage > 0.75f
}

@Composable
fun WordGuesserComponent(status: StatusWordGuesser, expectedText: String) {
    val color = mapOf(
        StatusWordGuesser.WRONG to MaterialTheme.colorScheme.error,
        StatusWordGuesser.DONE to Color.Green,
    )[status]?: MaterialTheme.colorScheme.onSurface
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(expectedText,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
            fontSize = 22.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LessonScreen()
}

enum class StatusWordGuesser {
    NEW,
    WRONG,
    DONE
}