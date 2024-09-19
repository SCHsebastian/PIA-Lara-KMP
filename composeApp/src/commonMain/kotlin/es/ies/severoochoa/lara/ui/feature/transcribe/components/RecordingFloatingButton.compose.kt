package es.ies.severoochoa.lara.ui.feature.transcribe.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pialara.composeapp.generated.resources.Res
import pialara.composeapp.generated.resources.mic

@Composable
fun RecordingFloatingButton(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = MaterialTheme.colors.onPrimary
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = contentColor,
        modifier = Modifier.size(60.dp)
    ) {
        AnimatedContent(targetState = isRecording) { targetState: Boolean ->
            if (targetState){
                Canvas(modifier = modifier.size(30.dp)) {
                    drawRect(
                        color = contentColor,
                        size = Size(size.width, size.height)
                    )
                }
            }else{
                Image(painterResource(Res.drawable.mic), null)
            }
        }
    }
}

@Preview
@Composable
fun RecordingFloatingButtonStoppedPreview() {
    RecordingFloatingButton(isRecording = false, onClick = {})
}

@Preview
@Composable
fun RecordingFloatingButtonRecordingPreview() {
    RecordingFloatingButton(isRecording = true, onClick = {})
}