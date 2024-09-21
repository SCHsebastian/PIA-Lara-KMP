package es.ies.severoochoa.lara.common.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.AVFAudio.AVAudioApplication
import platform.AVFAudio.AVAudioApplicationRecordPermission
import platform.AVFAudio.AVAudioApplicationRecordPermissionDenied
import platform.AVFAudio.AVAudioApplicationRecordPermissionGranted
import platform.AVFAudio.AVAudioApplicationRecordPermissionUndetermined
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return PermissionsManager(callback)
}

actual class PermissionsManager actual constructor(private val callback: PermissionCallback) :
    PermissionHandler {
    @Composable
    override fun AskPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.RECORD_AUDIO -> {
                val status:  AVAudioApplicationRecordPermission =
                    remember { AVAudioApplication.sharedInstance().recordPermission }
                askAudioRecordPermission(status, permission, callback)
            }
        }
    }
    private fun askAudioRecordPermission(
        status: AVAudioApplicationRecordPermission, permission: PermissionType, callback: PermissionCallback
    ) {
        when (status) {
            AVAudioApplicationRecordPermissionGranted -> {
                callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            }
            AVAudioApplicationRecordPermissionUndetermined -> {
                return AVAudioApplication.requestRecordPermissionWithCompletionHandler { isGranted ->
                    if (isGranted) {
                        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                    } else {
                        callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                    }
                }
            }
            AVAudioApplicationRecordPermissionDenied -> {
                callback.onPermissionStatus(permission, PermissionStatus.DENIED)
            }
            else -> error("unknown voice status $status")
        }
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.RECORD_AUDIO -> {
                val status: AVAudioApplicationRecordPermission =
                    remember { AVAudioApplication.sharedInstance().recordPermission }
                status == AVAudioApplicationRecordPermissionGranted
            }
        }
    }

    @Composable
    override fun LaunchSettings() {
        NSURL.URLWithString(UIApplicationOpenSettingsURLString)?.let {
            UIApplication.sharedApplication.openURL(it)
        }
    }

}