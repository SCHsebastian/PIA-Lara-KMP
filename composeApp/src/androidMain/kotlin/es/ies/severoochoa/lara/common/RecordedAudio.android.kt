package es.ies.severoochoa.lara.common

actual class RecordedAudio(
    private val audioBytes: ByteArray
) {
    actual fun getAudioBytes(): ByteArray = audioBytes
}