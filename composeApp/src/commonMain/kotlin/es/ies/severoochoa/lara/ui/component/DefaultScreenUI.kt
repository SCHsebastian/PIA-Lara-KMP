package es.ies.severoochoa.lara.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import es.ies.severoochoa.lara.domain.core.NetworkState
import es.ies.severoochoa.lara.domain.core.ProgressBarState
import es.ies.severoochoa.lara.domain.core.Queue
import es.ies.severoochoa.lara.domain.core.UIComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pialara.composeapp.generated.resources.Res
import pialara.composeapp.generated.resources.no_wifi

/**
 * @param queue: Dialogs
 * @param content: The content of the UI.
 */
@Composable
fun DefaultScreenUI(
    queue: Queue<UIComponent> = Queue(mutableListOf()),
    onRemoveHeadFromQueue: () -> Unit = {},
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    networkState: NetworkState = NetworkState.Good,
    onTryAgain: () -> Unit = {},
    titleToolbar: String? = null,
    startIconToolbar: ImageVector? = null,
    endIconToolbar: ImageVector? = null,
    onClickStartIconToolbar: () -> Unit = {},
    onClickEndIconToolbar: () -> Unit = {},
    content: @Composable () -> Unit,
) {

    Scaffold(
        topBar = {
            if (titleToolbar != null) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (startIconToolbar != null) {
                        CircleButton(
                            imageVector = startIconToolbar,
                            onClick = { onClickStartIconToolbar() })
                    } else {
                        Spacer_16dp()
                    }
                    Text(titleToolbar, style = MaterialTheme.typography.titleLarge)

                    if (endIconToolbar != null) {
                        CircleButton(
                            imageVector = endIconToolbar,
                            onClick = { onClickEndIconToolbar() })
                    } else {
                        Spacer_16dp()
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            content()
            // process the queue
            if (!queue.isEmpty()) {
                queue.peek()?.let { uiComponent ->
                    if (uiComponent is UIComponent.DialogSimple) {
                        CreateUIComponentDialog(
                            title = uiComponent.title,
                            description = uiComponent.description,
                            onRemoveHeadFromQueue = onRemoveHeadFromQueue
                        )
                    }
                    if (uiComponent is UIComponent.ToastSimple) {
                        ShowSnackbar(
                            title = uiComponent.title,
                            snackbarVisibleState = true,
                            onDismiss = onRemoveHeadFromQueue,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
            if (networkState == NetworkState.Failed && progressBarState == ProgressBarState.Idle) {
                FailedNetworkScreen(onTryAgain = onTryAgain)
            }

            if (progressBarState is ProgressBarState.ScreenLoading || progressBarState is ProgressBarState.FullScreenLoading) {
                CircularProgressIndicator()
            }

        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable

fun FailedNetworkScreen(onTryAgain: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(painterResource(Res.drawable.no_wifi), null)
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = "You are currently offline, please reconnect and try again.",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))

        DefaultButton(
            text = "Try Again",
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    DEFAULT__BUTTON_SIZE
                )
        ) {
            onTryAgain()
        }


    }

}













