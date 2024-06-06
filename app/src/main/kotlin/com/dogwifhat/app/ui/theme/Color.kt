package com.dogwifhat.app.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object AppColor {

  val Accent = Color(0xFFFFBD5B)

  val ButtonPrimary = Accent
  val ButtonPrimaryText = Color.White

  val MainGradient = Brush.horizontalGradient(
    listOf(Color(0xFFFF8F77), Color(0xFFFFBD5A)),
  )

  val AlertGradient = Brush.horizontalGradient(
    listOf(Color(0xFFDA2E2E), Color(0xFF741818)),
  )

  val BackGradient = Brush.verticalGradient(
    listOf(Color(0xFF222831), Color(0xFF13161B)),
  )
}
