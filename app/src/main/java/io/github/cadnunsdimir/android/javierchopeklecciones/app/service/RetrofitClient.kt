package io.github.cadnunsdimir.android.javierchopeklecciones.app.service

import io.github.cadnunsdimir.android.javierchopeklecciones.BuildConfig
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth.AuthInterceptor
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth.AuthRestClient
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth.TokenManager
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.lesson.ExerciseApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private lateinit var tokenManager: TokenManager

    fun init(context: TokenManager) {
        tokenManager = context
    }
    val exerciseApi: ExerciseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    // Tempo para estabelecer a conexão inicial TCP/TLS
                    .connectTimeout(15, TimeUnit.SECONDS)

                    // Tempo limite entre pacotes recebidos (O MAIS IMPORTANTE PARA LLM)
                    // O ideal é 45s a 60s para dar margem a oscilações da API do Gemini sem falhar a requisição do usuário
                    .readTimeout(60, TimeUnit.SECONDS)

                    // Tempo máximo para envio dos dados da requisição
                    .writeTimeout(15, TimeUnit.SECONDS)

                    // (Opcional) Teto absoluto para toda a transação HTTP
                    .callTimeout(90, TimeUnit.SECONDS)
                    .addInterceptor(AuthInterceptor(tokenManager))
                    .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseApiService::class.java)
    }

    val authApi: AuthRestClient by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthRestClient::class.java)
    }
}