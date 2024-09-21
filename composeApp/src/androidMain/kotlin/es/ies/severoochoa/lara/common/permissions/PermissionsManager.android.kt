package es.ies.severoochoa.lara.common.permissions

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return remember { PermissionsManager(callback) }
}

actual class PermissionsManager actual constructor(private val callback: PermissionCallback) :
    PermissionHandler {
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun AskPermission(permission: PermissionType) {
        val lifecycleOwner = LocalLifecycleOwner.current

        when (permission) {
            PermissionType.RECORD_AUDIO -> {
                val recordAudioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
                LaunchedEffect(recordAudioPermissionState) {
                    val permissionResult = recordAudioPermissionState.status
                    if (!permissionResult.isGranted) {
                        if (permissionResult.shouldShowRationale) {
                            callback.onPermissionStatus(
                                permission, PermissionStatus.SHOW_RATIONAL
                            )
                        } else {
                            lifecycleOwner.lifecycleScope.launch {
                                recordAudioPermissionState.launchPermissionRequest()
                            }
                        }
                    } else {
                        callback.onPermissionStatus(
                            permission, PermissionStatus.GRANTED
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.RECORD_AUDIO -> {
                val cameraPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
                cameraPermissionState.status.isGranted
            }
        }
    }

    @Composable
    override fun LaunchSettings() {
        val context = LocalContext.current
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also {
            context.startActivity(it)
        }
    }
}