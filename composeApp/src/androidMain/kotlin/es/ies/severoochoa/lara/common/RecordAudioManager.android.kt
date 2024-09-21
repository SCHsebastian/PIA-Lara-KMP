package es.ies.severoochoa.lara.common

import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import es.ies.severoochoa.lara.domain.constants.CUSTOM_TAG
import java.io.IOException
import kotlin.io.path.pathString
import kotlin.io.path.readBytes

@Composable
actual fun rememberRecordAudioManager(onResult: (RecordedAudio?) -> Unit): RecordAudioManager {
    val context = LocalContext.current
    val tempAudioFile = kotlin.io.path.createTempFile(
        prefix = "audio_",
        suffix = ".3gp",
    )

    var mediaRecorder: MediaRecorder? = null

    var isRecording = false

    return RecordAudioManager(
        onRecord = {
            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                MediaRecorder()
            }.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setAudioSamplingRate(44100)
                setAudioEncodingBitRate(96000)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(tempAudioFile.pathString)
                try {
                    prepare()
                } catch (e: IOException) {
                    Log.e(CUSTOM_TAG, "Error preparing MediaRecorder: ${e.message}", e)
                }
            }
            mediaRecorder?.start()
            isRecording = true
        },
        onStop = {
            if (isRecording){
                try {
                    mediaRecorder?.stop()
                    isRecording = false
                    onResult(RecordedAudio(
                        audioBytes = tempAudioFile.readBytes(),
                    ))
                }catch (e: Exception){
                    Log.e(CUSTOM_TAG, "Error stopping recording: ${e.message}", e)
                }finally {
                    mediaRecorder?.release()
                }
            }
        },
        checkRecording = {
            isRecording
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