package es.ies.severoochoa.lara.common

import androidx.compose.runtime.Composable
import io.ktor.util.date.getTimeMillis
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioQuality
import platform.AVFAudio.AVAudioRecorder
import platform.AVFAudio.AVEncoderAudioQualityKey
import platform.AVFAudio.AVFormatIDKey
import platform.AVFAudio.AVNumberOfChannelsKey
import platform.AVFAudio.AVSampleRateKey
import platform.CoreAudioTypes.kAudioFormatMPEG4AAC
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL.Companion.fileURLWithPath
import platform.Foundation.temporaryDirectory

private const val SampleRate = 44100

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberRecordAudioManager(onResult: (RecordedAudio?) -> Unit): RecordAudioManager{
    val timestamp = getTimeMillis().toString()
    val fileName = "${timestamp}.mp4"
    val place = "${NSFileManager.defaultManager.temporaryDirectory.path}/$fileName"
    val url = fileURLWithPath(place)

    val settings = mapOf<Any?, Any>(
        AVFormatIDKey to kAudioFormatMPEG4AAC,
        AVSampleRateKey to SampleRate,
        AVNumberOfChannelsKey to 1,
        AVEncoderAudioQualityKey to AVAudioQuality.MAX_VALUE
    )

    var recorder : AVAudioRecorder? = null

    return RecordAudioManager(
        onRecord = {
            recorder = AVAudioRecorder(
                url,
                settings,
                null
            ).also {
                it.prepareToRecord()
                it.record()
            }
        },
        onStop = {
            recorder?.stop()
            onResult(RecordedAudio(
                audioBytes = NSFileManager.defaultManager.contentsAtPath(place)?.bytes as ByteArray
            ))
        },
        checkRecording = {
            recorder?.isRecording() ?: false
        }
    )
}


actual class RecordAudioManager actual constructor(private val onRecord: () -> Unit, private val onStop: () -> Unit, private val checkRecording: () -> Boolean) {
    actual fun record() {
        onRecord()
    }

    actual fun stop() {
        onStop()
    }

    actual fun isRecording(): Boolean = checkRecording()
}