package com.dogwifhat.app.common.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GradientBorderButton(
  text: String,
  gradientBrush: Brush,
  modifier: Modifier = Modifier,
  @DrawableRes imageRes: Int? = null,
  onClick: () -> Unit,
) {
  val textStyle = LocalTextStyle.current.copy(
    color = Color.White,
    fontSize = 14.sp,
    fontWeight = FontWeight.W600,
    textAlign = TextAlign.Center,
  )
  Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .fillMaxWidth()
      .heightIn(min = 44.dp)
      .border(1.dp, gradientBrush, RoundedCornerShape(8.dp))
      .clickable(onClick = onClick),
  ) {
    if (imageRes != null) {
      Image(
        painter = painterResource(imageRes),
        contentDescription = null,
        modifier = Modifier.size(25.dp),
      )
      Spacer(modifier = Modifier.size(8.dp))
    }
    Text(
      text = text,
      style = textStyle,
    )
  }
}
