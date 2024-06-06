package com.dogwifhat.app.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dogwifhat.app.R
import com.dogwifhat.app.ui.theme.AppColor

@Composable
fun ErrorScreen(
  paddingValues: PaddingValues = PaddingValues(),
  title: String? = stringResource(R.string.error_generic_title),
  description: String? = stringResource(R.string.error_generic_description),
  buttonLabel: String = stringResource(R.string.error_generic_button),
  @DrawableRes iconResId: Int? = null,
  @DrawableRes imageResId: Int? = null,
  isLoading: Boolean = false,
  onButtonClick: (() -> Unit)? = null,
) {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .padding(paddingValues)
      .fillMaxSize()
      .background(AppColor.BackGradient),
  ) {
    when {
      iconResId != null -> {
        Icon(
          painter = painterResource(iconResId),
          modifier = Modifier.size(64.dp),
          tint = AppColor.Accent,
          contentDescription = null,
        )
      }

      imageResId != null -> {
        Image(
          painter = painterResource(imageResId),
          modifier = Modifier.size(150.dp),
          contentDescription = null,
        )
      }
    }
    if (title != null) {
      Spacer(modifier = Modifier.size(20.dp))
      Text(
        text = title,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 32.dp),
      )
    }
    if (description != null) {
      Spacer(modifier = Modifier.size(20.dp))
      Text(
        text = description,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 48.dp),
      )
    }
    if (onButtonClick != null) {
      Spacer(modifier = Modifier.size(32.dp))
      BasedButton(
        text = buttonLabel,
        style = ButtonStyle.Primary,
        onClick = onButtonClick,
        size = ButtonSize.M,
        isLoading = isLoading,
      )
    }
  }
}

@Preview
@Composable
fun ErrorScreenPreview() {
  ErrorScreen(
    onButtonClick = {},
    imageResId = R.drawable.img_settings,
  )
}
