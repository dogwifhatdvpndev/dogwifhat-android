package com.dogwifhat.app.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GradientButton(
  onClick: () -> Unit,
  brush: Brush,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  Button(
    colors = ButtonDefaults.buttonColors(
      containerColor = Color.Transparent,
      contentColor = Color.White,
    ),
    contentPadding = PaddingValues(),
    shape = RoundedCornerShape(8.dp),
    onClick = onClick,
    modifier = modifier.fillMaxWidth(),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier
        .heightIn(min = 48.dp)
        .fillMaxWidth()
        .background(brush)
        .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
      content()
    }
  }
}
