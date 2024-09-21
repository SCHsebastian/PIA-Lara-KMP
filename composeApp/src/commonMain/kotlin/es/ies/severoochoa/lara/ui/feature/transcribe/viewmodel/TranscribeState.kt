package es.ies.severoochoa.lara.ui.feature.transcribe.viewmodel

import es.ies.severoochoa.lara.domain.TranscriptionModel
import es.ies.severoochoa.lara.domain.core.NetworkState
import es.ies.severoochoa.lara.domain.core.ProgressBarState
import es.ies.severoochoa.lara.domain.core.Queue
import es.ies.severoochoa.lara.domain.core.UIComponent

data class TranscribeState(
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
    val networkState: NetworkState = NetworkState.Good,
    val models: List<TranscriptionModel> = listOf(TranscriptionModel("sample1"), TranscriptionModel("sample2"), TranscriptionModel("sample3")),
    val selectedModel: TranscriptionModel = TranscriptionModel("sample1"),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val isSelectingModel: Boolean = false,
    val isRecording: Boolean = false,
    val recordingPowerRatios: List<Float> = emptyList(),
    val transcription: String = "",
    )