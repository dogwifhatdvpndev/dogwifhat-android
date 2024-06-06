package com.dogwifhat.app.ui.screens.intro.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
  state: PagerState,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    repeat(state.pageCount) { index ->
      PagerIndicatorDot(index == state.currentPage)
    }
  }
}

@Composable
private fun PagerIndicatorDot(isActive: Boolean) {
  Row {
    val dotColor = if (isActive) {
      Color(0xFFFFFFFF)
    } else {
      Color(0x66FFFFFF)
    }
    Box(
      modifier = Modifier
        .clip(CircleShape)
        .size(10.dp)
        .background(color = dotColor),
    )
  }
}
