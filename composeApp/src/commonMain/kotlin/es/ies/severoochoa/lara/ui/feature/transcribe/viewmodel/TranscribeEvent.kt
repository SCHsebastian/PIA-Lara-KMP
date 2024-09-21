package es.ies.severoochoa.lara.ui.feature.transcribe.viewmodel

import es.ies.severoochoa.lara.common.RecordedAudio
import es.ies.severoochoa.lara.domain.TranscriptionModel
import es.ies.severoochoa.lara.domain.core.UIComponentState

sealed class TranscribeEvent {
    data object OnStartRecording : TranscribeEvent()
    data object OnStopRecording : TranscribeEvent()
    data object OnRetryNetwork : TranscribeEvent()
    data object OnRemoveHeadFromQueue : TranscribeEvent()
    data object OnSelectModel : TranscribeEvent()
    data object OnDismissModel : TranscribeEvent()
    data class OnUpdatePermissionDialog(val state: UIComponentState) : TranscribeEvent()
    data class OnModelSelected(val model: TranscriptionModel) : TranscribeEvent()
    data class OnCopyTranscription(val transcription: String) : TranscribeEvent()
    data class StartTranscription(val recordedAudio: RecordedAudio?) : TranscribeEvent()

}