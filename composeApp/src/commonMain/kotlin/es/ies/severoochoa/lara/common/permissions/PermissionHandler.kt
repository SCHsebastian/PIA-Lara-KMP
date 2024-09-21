package es.ies.severoochoa.lara.common.permissions

import androidx.compose.runtime.Composable

interface PermissionHandler {
    @Composable
    fun AskPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun LaunchSettings()

}