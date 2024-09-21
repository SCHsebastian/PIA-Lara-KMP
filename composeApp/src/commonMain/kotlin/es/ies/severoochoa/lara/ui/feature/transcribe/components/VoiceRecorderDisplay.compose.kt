package es.ies.severoochoa.lara.ui.feature.transcribe.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import es.ies.severoochoa.lara.theme.PiaLaraTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

@Composable
fun VoiceRecorderDisplay(
    powerRatios: List<Float>,
    modifier: Modifier = Modifier
) {
    val primary = MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .padding(
                horizontal = 32.dp,
                vertical = 8.dp
            )
            .drawBehind {
                val powerRatioWith = 3.dp.toPx()
                val powerRatioCount = (size.width / (2 * powerRatioWith)).toInt()
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height
                ){
                    powerRatios
                        .takeLast(powerRatioCount)
                        .reversed()
                        .forEachIndexed { index, ratio ->
                            val yTopStart = center.y - (size.height / 2f) * ratio
                            drawRoundRect(
                                color = primary,
                                topLeft = Offset(
                                    x = size.width - index * 2 * powerRatioWith,
                                    y =yTopStart
                                ),
                                size = Size(
                                    width = powerRatioWith,
                                    height = (center.y - yTopStart) * 2f
                                ),
                                cornerRadius = CornerRadius(100f)
                            )
                        }
                }
            }
    ){

    }
}

@Preview
@Composable
fun VoiceRecorderDisplayPreview() {
    PiaLaraTheme {
        VoiceRecorderDisplay(powerRatios = (0..60).map {
            Random.nextFloat()
        }, Modifier.fillMaxWidth().height(100.dp))
    }
}