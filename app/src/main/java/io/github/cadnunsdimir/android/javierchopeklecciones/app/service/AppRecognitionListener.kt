package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log

class SpeechRecognitionListener(
    private val onResult: (String) -> Unit,
    private val onErrorListener: (Int) -> Unit,
    private val onEndSpeech: () -> Unit
) : RecognitionListener {

    override fun onResults(results: Bundle?) {
        val languageTag = results?.getString(
            RecognizerIntent.EXTRA_LANGUAGE
        )

        // Opcional: O modelo de idioma usado
        val languageModel = results?.getString(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL
        )

        Log.d("SpeechRecognitionListener", "Idioma Recognizado (Tag): $languageTag")
        Log.d("SpeechRecognitionListener", "Modelo de Idioma: $languageModel")
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (!matches.isNullOrEmpty()) {
            onResult(matches[0])
        }
    }

    override fun onRmsChanged(p0: Float) {
    }

    override fun onBeginningOfSpeech() {
    }

    override fun onBufferReceived(p0: ByteArray?) {
    }

    override fun onEndOfSpeech() {
        onEndSpeech()
    }

    override fun onError(error: Int) {
        onErrorListener(error)
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
    }

    override fun onPartialResults(p0: Bundle?) {
    }

    override fun onReadyForSpeech(p0: Bundle?) {
    }
}