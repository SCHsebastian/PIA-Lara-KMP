package es.ies.severoochoa.lara.ui.feature.transcribe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.ies.severoochoa.lara.domain.TranscriptionModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class TranscibreScreenViewModel
constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun onEvent(uiEvent: UiEvent){
        when(uiEvent){
            UiEvent.OnStartRecording -> {
                _state.update { it.copy(isRecording = true) }
                startRecording()
            }
            UiEvent.OnStopRecording -> _state.update { it.copy(isRecording = false) }
            UiEvent.OnDismissModel -> _state.update { it.copy(isSelectingModel = false) }
            is UiEvent.OnModelSelected -> _state.update { it.copy(selectedModel = uiEvent.model, isSelectingModel = false) }
            UiEvent.OnSelectModel -> _state.update { it.copy(isSelectingModel = true) }
        }
    }

    private fun startRecording() {
        //for 1 in 1000 send power ratio to state
        var powerRatios: MutableList<Float> = mutableListOf()
        viewModelScope.launch {
            powerRatios = powerRatios.apply {
                addAll((0..1000).map {
                    Random.nextFloat()
                })
                _state.update {
                    it.copy(recordingPowerRatios = powerRatios)
                }
                delay(10000)
            }
            _state.update { it.copy(isRecording = false) }
            startTranscription()
        }
    }

    private fun startTranscription() {
        _state.update { it.copy(isLoadingTranscription = true) }
        viewModelScope.launch {
            delay(2000)
            _state.update { it.copy(isLoadingTranscription = false, transcription = "This is a transcription") }
        }
    }

    data class State(
        val isRecording: Boolean = false,
        val isSelectingModel: Boolean = false,
        val models: List<TranscriptionModel> = listOf(TranscriptionModel("OpenAI"), TranscriptionModel( "Gemini"), TranscriptionModel("Whisper")),
        val selectedModel: TranscriptionModel = TranscriptionModel("OpenAI"),
        val isLoadingTranscription: Boolean = false,
        val transcription: String = "",
        val recordingPowerRatios: List<Float> = emptyList()
    )

    sealed class UiEvent{
        object OnStartRecording : UiEvent()
        object OnStopRecording : UiEvent()
        object OnSelectModel : UiEvent()
        object OnDismissModel : UiEvent()
        data class OnModelSelected(val model: TranscriptionModel) : UiEvent()
    }
}