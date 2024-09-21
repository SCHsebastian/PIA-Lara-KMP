package es.ies.severoochoa.lara.common

import androidx.compose.runtime.Composable

@Composable
expect fun rememberRecordAudioManager(onResult: (RecordedAudio?) -> Unit): RecordAudioManager

expect class RecordAudioManager(
    onRecord: () -> Unit,
    onStop: () -> Unit,
    checkRecording: () -> Boolean,
) {
    fun record()
    fun stop()
    fun isRecording(): Boolean
}