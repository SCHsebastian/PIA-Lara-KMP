package es.ies.severoochoa.lara.ui.feature.transcribe.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.ies.severoochoa.lara.common.RecordedAudio
import es.ies.severoochoa.lara.domain.TranscriptionModel
import es.ies.severoochoa.lara.domain.constants.CUSTOM_TAG
import es.ies.severoochoa.lara.domain.core.ProgressBarState
import es.ies.severoochoa.lara.domain.core.Queue
import es.ies.severoochoa.lara.domain.core.UIComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TranscibreScreenViewModel
constructor(

) : ViewModel() {

    val state: MutableState<TranscribeState> = mutableStateOf(TranscribeState())

    fun onEvent(event: TranscribeEvent){
        when(event){
            is TranscribeEvent.OnCopyTranscription -> onCopyTranscription(event.transcription)
            TranscribeEvent.OnDismissModel -> onDismissModel()
            is TranscribeEvent.OnModelSelected -> onModelSelected(event.model)
            TranscribeEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            TranscribeEvent.OnSelectModel -> onSelectModel()
            TranscribeEvent.OnStartRecording -> startRecording()
            TranscribeEvent.OnStopRecording -> onStopRecording()
            is TranscribeEvent.StartTranscription -> startTranscription(event.recordedAudio)
            else -> Unit
        }
    }

    private fun onSelectModel() {
        state.value = state.value.copy(isSelectingModel = true)
    }

    private fun onCopyTranscription(transcription: String){
        viewModelScope.launch {
            appendToMessageQueue(UIComponent.ToastSimple("Transcription copied to clipboard"))

            //_effect.send(Effect.ShareTranscription(uiEvent.transcription))
        }
    }

    private fun onModelSelected(model: TranscriptionModel) {
        state.value = state.value.copy(selectedModel = model, isSelectingModel = false)
    }

    private fun onDismissModel() {
        state.value = state.value.copy (isSelectingModel = false)
    }

    private fun onStopRecording() {
        state.value = state.value.copy(isRecording = false)
    }

    private fun startRecording() {
        state.value = state.value.copy(isRecording = true)
    }

    private fun startTranscription(recordedAudio: RecordedAudio?) {
        state.value = state.value.copy(progressBarState = ProgressBarState.FullScreenLoading)
        viewModelScope.launch {
            delay(2000)
            state.value = state.value.copy(progressBarState = ProgressBarState.Idle, transcription = "This is a transcription of ${recordedAudio?.getAudioBytes()?.size} bytes")
        }
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
        state.value = state.value.copy(errorQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove() // can throw exception if empty
            state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            println("${CUSTOM_TAG}: removeHeadMessage: Nothing to remove from DialogQueue")
        }
    }
}