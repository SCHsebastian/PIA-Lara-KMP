package es.ies.severoochoa.lara.ui.feature.transcribe.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pialara.composeapp.generated.resources.Res
import pialara.composeapp.generated.resources.mic

@Composable
fun RecordingFloatingButton(
    modifier: Modifier = Modifier,
    canRecord: Boolean,
    isRecording: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = MaterialTheme.colorScheme.onPrimary
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = contentColor,
        modifier = Modifier.size(60.dp),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        AnimatedContent(targetState = canRecord){
            if (it) {
                AnimatedContent(
                    targetState = isRecording,
                    content = { targetState: Boolean ->
                        if (targetState){
                            Canvas(modifier = modifier.size(30.dp)) {
                                drawRect(
                                    color = contentColor,
                                    size = Size(size.width, size.height)
                                )
                            }
                        }else{
                            Image(painter = painterResource(Res.drawable.mic), null)
                        }
                    }
                )
            } else {
                Icon(imageVector = Icons.Default.Refresh, null)
            }
        }
    }
}