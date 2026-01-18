package io.delight.effect

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

internal data class AudioPermissionState(
    val onPermissionRequest: () -> Unit,
    val onOpenAppSettings: () -> Unit
)

@Composable
internal fun AudioPermissionEffect(
    onPermissionResult: (Boolean) -> Unit
): AudioPermissionState {
    val context = LocalContext.current

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onPermissionResult(isGranted)
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            onPermissionResult(true)
        } else {
            launcher.launch(permission)
        }
    }

    val openAppSettings: () -> Unit = {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )
        context.startActivity(intent)
    }

    val onPermissionRequest: () -> Unit = {
        val activity = context as? Activity
        if (activity != null) {
            val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
            if (!shouldShowRationale) {
                openAppSettings()
            } else {
                launcher.launch(permission)
            }
        } else {
            launcher.launch(permission)
        }
    }

    return AudioPermissionState(
        onPermissionRequest = onPermissionRequest,
        onOpenAppSettings = openAppSettings
    )
}