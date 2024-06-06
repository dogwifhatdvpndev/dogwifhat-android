package com.dogwifhat.app.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dogwifhat.app.R

@Composable
fun TopBar(
  title: String,
  navigateBack: () -> Unit,
) {
  Box(
    modifier = Modifier
      .statusBarsPadding()
      .padding(horizontal = 20.dp)
      .padding(top = 20.dp, bottom = 8.dp)
      .fillMaxWidth(),
  ) {
    IconButton(
      onClick = navigateBack,
      modifier = Modifier.size(34.dp),
    ) {
      Icon(
        painter = painterResource(R.drawable.ic_back),
        contentDescription = "Go back",
        modifier = Modifier.size(18.dp),
        tint = Color.White,
      )
    }
    Text(
      text = title,
      color = Color.White,
      fontSize = 14.sp,
      fontWeight = FontWeight.W500,
      maxLines = 1,
      modifier = Modifier.align(Alignment.Center),
    )
  }
}
