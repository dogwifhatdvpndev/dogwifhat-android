package com.dogwifhat.app.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dogwifhat.app.R

@Composable
fun BaseRow(
  title: String,
  modifier: Modifier = Modifier,
  subtitle: String? = null,
  @DrawableRes imageRes: Int? = null,
  @DrawableRes iconRes: Int? = null,
  onClick: () -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .clickable(onClick = onClick)
      .height(56.dp)
      .padding(horizontal = 12.dp)
      .fillMaxWidth(),
  ) {
    when {
      imageRes != null -> {
        Image(
          painter = painterResource(imageRes),
          contentDescription = null,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(24.dp)
            .clip(CircleShape),
        )
        Spacer(modifier = Modifier.size(16.dp))
      }

      iconRes != null -> {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.size(24.dp),
        ) {
          Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp),
          )
        }
        Spacer(modifier = Modifier.size(16.dp))
      }
    }
    Column {
      Text(
        text = title,
        color = Color.White,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        lineHeight = 14.sp,
      )
      if (subtitle != null) {
        Spacer(modifier = Modifier.size(4.dp))
        Text(
          text = subtitle,
          color = Color.White.copy(alpha = 0.6f),
          fontWeight = FontWeight.W400,
          fontSize = 12.sp,
          lineHeight = 12.sp,
        )
      }
    }
    Spacer(modifier = Modifier.weight(1f))
    Icon(
      painter = painterResource(R.drawable.ic_arrow),
      contentDescription = null,
      tint = Color.White,
      modifier = Modifier
        .size(24.dp)
        .padding(5.dp),
    )
  }
}
