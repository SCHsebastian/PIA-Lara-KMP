package es.ies.severoochoa.lara.ui.feature.transcribe

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import es.ies.severoochoa.lara.PiaLaraTheme
import es.ies.severoochoa.lara.ui.feature.transcribe.TranscibreScreenViewModel.UiEvent
import es.ies.severoochoa.lara.ui.feature.transcribe.components.RecordingFloatingButton
import es.ies.severoochoa.lara.ui.feature.transcribe.components.Transcription
import es.ies.severoochoa.lara.ui.feature.transcribe.components.TranscriptionModelDropDown
import es.ies.severoochoa.lara.ui.feature.transcribe.components.VoiceRecorderDisplay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TranscribeScreen(navController: NavHostController) {
    val viewModel =  koinViewModel<TranscibreScreenViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            RecordingFloatingButton(
                isRecording = state.isRecording,
                onClick = {
                    if (state.isRecording) {
                        viewModel.onEvent(UiEvent.OnStopRecording)
                    } else {
                        viewModel.onEvent(UiEvent.OnStartRecording)
                    }
                },
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ){ padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TranscriptionModelDropDown(
                        selectedModel = state.selectedModel,
                        isOpen = state.isSelectingModel,
                        onDismiss = {viewModel.onEvent(UiEvent.OnDismissModel)},
                        onItemClick = {viewModel.onEvent(UiEvent.OnSelectModel)},
                        onItemSelect = {viewModel.onEvent(UiEvent.OnModelSelected(it))},
                        items = state.models
                    )
                }
            }
            item {
                AnimatedContent(targetState = state.isRecording){
                    if (it){
                        VoiceRecorderDisplay(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            powerRatios = state.recordingPowerRatios
                        )
                    }else{
                        Transcription(
                            isLoading = state.isLoadingTranscription,
                            transcription = state.transcription,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TranscribeScreenPreview() {
    PiaLaraTheme {
        TranscribeScreen(rememberNavController())
    }
}