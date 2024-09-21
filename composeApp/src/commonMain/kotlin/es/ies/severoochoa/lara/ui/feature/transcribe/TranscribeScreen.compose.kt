package es.ies.severoochoa.lara.ui.feature.transcribe

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.ies.severoochoa.lara.common.permissions.PermissionCallback
import es.ies.severoochoa.lara.common.permissions.PermissionStatus
import es.ies.severoochoa.lara.common.permissions.PermissionType
import es.ies.severoochoa.lara.common.permissions.createPermissionsManager
import es.ies.severoochoa.lara.common.rememberRecordAudioManager
import es.ies.severoochoa.lara.domain.core.ProgressBarState
import es.ies.severoochoa.lara.domain.core.UIComponentState
import es.ies.severoochoa.lara.ui.component.DefaultScreenUI
import es.ies.severoochoa.lara.ui.feature.transcribe.components.RecordingFloatingButton
import es.ies.severoochoa.lara.ui.feature.transcribe.components.Transcription
import es.ies.severoochoa.lara.ui.feature.transcribe.components.TranscriptionModelDropDown
import es.ies.severoochoa.lara.ui.feature.transcribe.components.VoiceRecorderDisplay
import es.ies.severoochoa.lara.ui.feature.transcribe.viewmodel.TranscribeEvent
import es.ies.severoochoa.lara.ui.feature.transcribe.viewmodel.TranscribeState
import kotlinx.coroutines.launch

@Composable
fun TranscribeScreen(
    state: TranscribeState,
    events: (TranscribeEvent) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var canRecordAudio by remember { mutableStateOf(value = false) }
    var checkPermission by remember { mutableStateOf(value = false) }

     val recordAudioManager = rememberRecordAudioManager {
        coroutineScope.launch {
            events(TranscribeEvent.StartTranscription(it))
        }
    }

    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.RECORD_AUDIO -> {
                            canRecordAudio = true
                            checkPermission = false
                        }
                    }
                }
                else -> {
                    events(TranscribeEvent.OnUpdatePermissionDialog(UIComponentState.Show))
                }
            }
        }
    })

    if (checkPermission) {
        permissionsManager.AskPermission(PermissionType.RECORD_AUDIO)
    } else {
        if (permissionsManager.isPermissionGranted(PermissionType.RECORD_AUDIO)){
            canRecordAudio = true
        }
    }

    if (canRecordAudio && state.isRecording){
        recordAudioManager.record()
    }else{
        if (recordAudioManager.isRecording()) {
            recordAudioManager.stop()
        }
    }

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(TranscribeEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        titleToolbar = "Habla con Lara",
        onTryAgain = { events(TranscribeEvent.OnRetryNetwork) },

    ) {
        Scaffold(
            floatingActionButton = {
                RecordingFloatingButton(
                    canRecord = canRecordAudio,
                    isRecording = state.isRecording,
                    onClick = {
                        if (canRecordAudio){
                            if (state.isRecording)
                                events(TranscribeEvent.OnStopRecording)
                            else
                                events(TranscribeEvent.OnStartRecording)
                        }else{
                            checkPermission = true
                        }
                    },
                )
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { padding ->
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
                            onDismiss = { events(TranscribeEvent.OnDismissModel) },
                            onItemClick = { events(TranscribeEvent.OnSelectModel) },
                            onItemSelect = { events(TranscribeEvent.OnModelSelected(it)) },
                            items = state.models
                        )
                    }
                }
                item {
                    AnimatedContent(targetState = state.isRecording) {
                        if (it) {
                            VoiceRecorderDisplay(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                powerRatios = state.recordingPowerRatios
                            )
                        } else {
                            Transcription(
                                modifier = Modifier.fillMaxWidth(),
                                isLoading = state.progressBarState is ProgressBarState.FullScreenLoading,
                                transcription = state.transcription,
                                onCopyClick = {
                                    events(TranscribeEvent.OnCopyTranscription(it))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
