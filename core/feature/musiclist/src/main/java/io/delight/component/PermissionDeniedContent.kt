package io.delight.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.delight.core.feature.musiclist.R
import io.delight.designsystem.theme.DelightMusicTheme

@Composable
internal fun PermissionDeniedContent(
    onPermissionRequest: () -> Unit,
    onOpenAppSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.permission_required_message),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onPermissionRequest) {
            Text(text = stringResource(R.string.permission_grant_button))
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onOpenAppSettings) {
            Text(text = stringResource(R.string.permission_settings_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PermissionDeniedContentPreview() {
    DelightMusicTheme {
        PermissionDeniedContent(
            onPermissionRequest = {},
            onOpenAppSettings = {}
        )
    }
}
