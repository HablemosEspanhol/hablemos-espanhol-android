package io.github.cadnunsdimir.android.javierchopeklecciones.ui.components

import android.Manifest
import android.speech.SpeechRecognizer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

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