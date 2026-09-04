package io.github.cadnunsdimir.android.javierchopeklecciones.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth.AuthState
import io.github.cadnunsdimir.android.javierchopeklecciones.app.service.auth.TokenManager
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.state.CefrLevel
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.state.HomeUiState
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.state.UserProgress
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.theme.JavierChopekLeccionesTheme
import io.github.cadnunsdimir.android.javierchopeklecciones.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    tokenManager: TokenManager,
    homeViewModel: HomeViewModel = viewModel(),
    onCreateAccountOrLogin: () -> Unit = {},
    onTakeLevelTest: () -> Unit = {},
    onContinueLesson: () -> Unit = {}
) {
    val authState by tokenManager.authState.collectAsState()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val message by homeViewModel.messageState.collectAsStateWithLifecycle()

    val resolvedState = when (authState) {
        AuthState.AUTHENTICATED -> uiState.takeIf { it is HomeUiState.LoggedIn }
            ?: HomeUiState.Loading
        else -> HomeUiState.LoggedOut
    }

    Scaffold(
        topBar = { HomeTopBar(state = resolvedState) }
    ) { innerPadding ->
        when (resolvedState) {
            HomeUiState.Loading -> LoadingText(
                text = message,
                modifier = Modifier.padding(innerPadding)
            )
            HomeUiState.LoggedOut -> LoggedOutHomeContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                onCreateAccountOrLogin = onCreateAccountOrLogin,
                onTakeLevelTest = onTakeLevelTest
            )
            is HomeUiState.LoggedIn -> LoggedInHomeContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                userProgress = resolvedState.userProgress,
                onContinueLesson = onContinueLesson
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(state: HomeUiState) {
    TopAppBar(
        title = {
            when (state) {
                HomeUiState.LoggedOut, HomeUiState.Loading -> Text(
                    text = "Hablemos Español!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                is HomeUiState.LoggedIn -> Unit
            }
        },
        actions = {
            if (state is HomeUiState.LoggedIn) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    ProfileAvatar(
                        initials = "JC",
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Default.Fireplace,
                        contentDescription = "Streak",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${state.userProgress.streakCount}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun LoggedOutHomeContent(
    modifier: Modifier = Modifier,
    onCreateAccountOrLogin: () -> Unit = {},
    onTakeLevelTest: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        HeroBanner()
        LevelTimeline(levels = CefrLevel.entries, highlightedLevel = CefrLevel.B1)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onCreateAccountOrLogin,
                modifier = Modifier.weight(1f)
            ) {
                Text("Criar Conta / Entrar")
            }
            OutlinedButton(
                onClick = onTakeLevelTest,
                modifier = Modifier.weight(1f)
            ) {
                Text("Fazer Teste de Nivelamento")
            }
        }
    }
}

@Composable
fun LoggedInHomeContent(
    modifier: Modifier = Modifier,
    userProgress: UserProgress,
    onContinueLesson: () -> Unit = {}
) {
    val completedRatio = userProgress.completedLessonsInCurrentLevel.toFloat() /
        userProgress.totalLessonsInCurrentLevel.toFloat()
    val remainingLessons = userProgress.totalLessonsInCurrentLevel -
        userProgress.completedLessonsInCurrentLevel

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        UserMetricsHeader(
            totalStudyDays = userProgress.totalStudyDays,
            currentLevel = userProgress.currentLevel
        )
        ProgressBarCard(
            currentLevel = userProgress.currentLevel,
            completedRatio = completedRatio,
            remainingLessons = remainingLessons,
            estimatedB1CompletionDate = userProgress.estimatedB1CompletionDate
        )
        LevelTimeline(
            levels = CefrLevel.entries,
            highlightedLevel = userProgress.currentLevel
        )
        NextLessonCTA(
            title = "Continuar: Lição 14",
            duration = "~ 5 min",
            onClick = onContinueLesson
        )
    }
}

@Composable
fun HeroBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AssistChip(onClick = {}, label = { Text("Jornada A1 a B1") })
            Text(
                text = "Aprenda Espanhol do A1 ao B1 em até 1 ano",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Curso e metodologia voltados aos alunos do Prof. Javier Chopek",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun LevelTimeline(
    levels: List<CefrLevel>,
    highlightedLevel: CefrLevel
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Trilha CEFR",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(levels) { level ->
                    val state = when {
                        level.ordinal < highlightedLevel.ordinal -> TimelineState.Completed
                        level == highlightedLevel -> TimelineState.Current
                        else -> TimelineState.Locked
                    }
                    TimelineLevelChip(level = level, state = state)
                }
            }
        }
    }
}

@Composable
fun UserMetricsHeader(
    totalStudyDays: Int,
    currentLevel: CefrLevel
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileAvatar(
                initials = currentLevel.shortName,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Estudando há ${totalStudyDays / 7} semanas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Nível atual: ${currentLevel.shortName} - ${currentLevel.label}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ProgressBarCard(
    currentLevel: CefrLevel,
    completedRatio: Float,
    remainingLessons: Int,
    estimatedB1CompletionDate: String
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Progresso do nível atual",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${currentLevel.shortName} em andamento",
                style = MaterialTheme.typography.bodyMedium
            )
            LinearProgressIndicator(
                progress = { completedRatio },
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "${(completedRatio * 100).toInt()}% concluído")
            Text(text = "$remainingLessons lições restantes para o B1")
            Text(text = "Previsão B1: $estimatedB1CompletionDate")
        }
    }
}

@Composable
fun NextLessonCTA(
    title: String,
    duration: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Continuar agora", style = MaterialTheme.typography.labelLarge)
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(text = duration, style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = onClick) {
                Text("Abrir")
            }
        }
    }
}

@Composable
private fun ProfileAvatar(
    initials: String,
    backgroundColor: Color,
    shape: Shape = CircleShape
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(shape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials.take(2),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

private enum class TimelineState { Completed, Current, Locked }

@Composable
private fun TimelineLevelChip(
    level: CefrLevel,
    state: TimelineState
) {
    val colors = when (state) {
        TimelineState.Completed -> FilterChipDefaults.filterChipColors()
        TimelineState.Current -> FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            labelColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
        TimelineState.Locked -> FilterChipDefaults.filterChipColors(
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    FilterChip(
        selected = state != TimelineState.Locked,
        onClick = {},
        label = { Text("${level.shortName}\n${level.label}", textAlign = TextAlign.Center) },
        leadingIcon = {
            when (state) {
                TimelineState.Completed -> Icon(Icons.Default.Check, contentDescription = null)
                TimelineState.Current -> Icon(Icons.Default.Cloud, contentDescription = null)
                TimelineState.Locked -> Unit
            }
        },
        enabled = state != TimelineState.Locked,
        colors = colors
    )
}

@Composable
private fun LoadingText(
    modifier: Modifier = Modifier,
    text: String? = null,
) {
    var dotCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            dotCount = (dotCount + 1) % 4
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${text ?: "Carregando"}${".".repeat(dotCount)}",
            modifier = modifier,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


private fun defaultPreviewProgress(): UserProgress {
    return UserProgress(
        currentLevel = CefrLevel.A2,
        totalStudyDays = 84,
        streakCount = 12,
        completedLessonsInCurrentLevel = 14,
        totalLessonsInCurrentLevel = 24,
        estimatedB1CompletionDate = "Outubro/2026"
    )
}

@Preview(showBackground = true)
@Composable
private fun LoggedOutHomePreview() {
    JavierChopekLeccionesTheme {
        Surface {
            LoggedOutHomeContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoggedInHomePreview() {
    JavierChopekLeccionesTheme {
        Surface {
            LoggedInHomeContent(
                userProgress = defaultPreviewProgress()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingHomePreview() {
    JavierChopekLeccionesTheme {
        Surface {
            LoadingText()
        }
    }
}
