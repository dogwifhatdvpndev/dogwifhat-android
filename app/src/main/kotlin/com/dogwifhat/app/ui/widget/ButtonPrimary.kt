package com.dogwifhat.app.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dogwifhat.app.ui.theme.AppColor

@Composable
fun BasedButton(
  text: String,
  style: ButtonStyle,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  size: ButtonSize = ButtonSize.L,
  isLoading: Boolean = false,
  @DrawableRes iconRes: Int? = null,
) {
  val colors = when (style) {
    ButtonStyle.Primary -> ButtonDefaults.outlinedButtonColors(
      containerColor = AppColor.ButtonPrimary,
      contentColor = AppColor.ButtonPrimaryText,
    )

    ButtonStyle.Secondary -> ButtonDefaults.outlinedButtonColors(
      contentColor = AppColor.ButtonPrimary,
    )
  }
  OutlinedButton(
    onClick = onClick,
    colors = colors,
    border = BorderStroke(1.dp, AppColor.ButtonPrimary),
    shape = RoundedCornerShape(8.dp),
    modifier = modifier
      .heightIn(min = size.minHeight)
      .widthIn(min = size.minWidth),
  ) {
    if (isLoading) {
      CircularProgressIndicator(
        color = LocalContentColor.current,
        modifier = Modifier.size(size.progressSize),
      )
    } else {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        if (iconRes != null) {
          Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp),
          )
          Spacer(modifier = Modifier.size(2.dp))
        }
        Text(
          text = text.uppercase(),
          fontSize = size.fontSize,
        )
      }
    }
  }
}

@Preview
@Composable
fun ButtonPrimaryPreview() {
  BasedButton(
    text = "Primary Button",
    style = ButtonStyle.Primary,
    onClick = {},
  )
}

enum class ButtonSize(
  val fontSize: TextUnit,
  val minHeight: Dp,
  val minWidth: Dp,
  val progressSize: Dp,
) {
  M(
    fontSize = 14.sp,
    minHeight = 42.dp,
    minWidth = 210.dp,
    progressSize = 24.dp,
  ),
  L(
    fontSize = 18.sp,
    minHeight = 60.dp,
    minWidth = 210.dp,
    progressSize = 42.dp,
  ),
}

enum class ButtonStyle {
  Primary,
  Secondary,
}
