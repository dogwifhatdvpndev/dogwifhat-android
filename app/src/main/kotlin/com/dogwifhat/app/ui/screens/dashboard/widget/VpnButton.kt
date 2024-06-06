package com.dogwifhat.app.ui.screens.dashboard.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dogwifhat.app.R

@Composable
fun VpnButton(
  state: VpnButtonState,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val imageRes = when (state) {
    VpnButtonState.Connected -> R.drawable.img_connected
    else -> R.drawable.img_disconnected
  }
  Image(
    painter = painterResource(imageRes),
    contentDescription = null,
    modifier = modifier
      .clip(CircleShape)
      .size(192.dp)
      .clickable(onClick = onClick),
  )
}

@Stable
enum class VpnButtonState {
  Disconnected,
  Connecting,
  Connected,
  Disconnecting,
}
