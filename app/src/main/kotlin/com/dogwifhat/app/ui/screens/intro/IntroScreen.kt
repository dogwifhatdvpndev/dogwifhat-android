package com.dogwifhat.app.ui.screens.intro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.sentinel.vpn.based.ext.openWeb
import com.dogwifhat.app.R
import com.dogwifhat.app.common.compose.GradientBorderButton
import com.dogwifhat.app.common.compose.GradientButton
import com.dogwifhat.app.ui.theme.AppColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreen(navigateToDashboard: () -> Unit) {
  val pagerState = rememberPagerState(
    pageCount = { 2 },
  )
  val scope = rememberCoroutineScope()

  val textStyle = LocalTextStyle.current.copy(
    color = Color.White,
    fontSize = 16.sp,
    fontWeight = FontWeight.W600,
    textAlign = TextAlign.Center,
  )

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .background(AppColor.BackGradient)
      .navigationBarsPadding(),
  ) {
    HorizontalPager(
      state = pagerState,
      modifier = Modifier.weight(1f),
    ) { pageNumber ->
      when (pageNumber) {
        0 -> IntroPage1(textStyle)
        1 -> IntroPage2(textStyle)
      }
    }
    GradientButton(
      onClick = {
        if (pagerState.currentPage < 1) {
          scope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
          }
        } else {
          navigateToDashboard.invoke()
        }
      },
      modifier = Modifier.padding(horizontal = 40.dp),
      brush = AppColor.MainGradient,
    ) {
      Text(
        text = stringResource(
          when (pagerState.currentPage) {
            1 -> R.string.intro_btn_get_started
            else -> R.string.intro_btn_next
          },
        ),
      )
    }
    Spacer(modifier = Modifier.size(20.dp))
  }
}

@Composable
private fun IntroPage1(textStyle: TextStyle) {
  Box(
    modifier = Modifier.fillMaxSize(),
  ) {
    Image(
      painter = painterResource(R.drawable.img_main_left),
      contentDescription = null,
      contentScale = ContentScale.FillWidth,
      modifier = Modifier
        .fillMaxWidth(0.4f)
        .align(Alignment.BottomStart),
    )
    Column(
      verticalArrangement = Arrangement.SpaceEvenly,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.fillMaxSize(),
    ) {
      Image(
        painter = painterResource(R.drawable.img_intro_title),
        contentDescription = null,
        modifier = Modifier.width(200.dp),
      )
      Text(
        text = stringResource(R.string.intro_page1_title),
        style = textStyle,
      )
      Image(
        painter = painterResource(R.drawable.img_intro_dont),
        contentDescription = null,
        modifier = Modifier.width(270.dp),
      )
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Image(
          painter = painterResource(R.drawable.img_intro_subtitle),
          contentDescription = null,
          modifier = Modifier.width(230.dp),
        )
        Spacer(modifier = Modifier.size(20.dp))
        Row {
          TextWithBorder(R.string.intro_page1_pill1)
          Spacer(modifier = Modifier.size(10.dp))
          TextWithBorder(R.string.intro_page1_pill2)
        }
        Spacer(modifier = Modifier.size(20.dp))
        TextWithBorder(R.string.intro_page1_pill3)
      }
      Image(
        painter = painterResource(R.drawable.img_sentinel),
        contentDescription = null,
        modifier = Modifier.width(150.dp),
      )
    }
  }
}

@Composable
private fun IntroPage2(textStyle: TextStyle) {
  val context = LocalContext.current
  Column(
    verticalArrangement = Arrangement.SpaceEvenly,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxSize(),
  ) {
    Column {
      Image(
        painter = painterResource(R.drawable.img_intro_dog),
        contentDescription = null,
        modifier = Modifier.width(130.dp),
      )
      Box(
        modifier = Modifier
          .size(width = 130.dp, height = 3.dp)
          .background(AppColor.MainGradient),
      )
    }
    Text(
      text = stringResource(R.string.intro_page2_title),
      style = textStyle,
      modifier = Modifier.padding(horizontal = 50.dp),
    )
    Text(
      text = buildAnnotatedString {
        append(stringResource(R.string.intro_page2_sentinel_docs))
        withStyle(SpanStyle(color = AppColor.Accent, textDecoration = TextDecoration.Underline)) {
          append("\ndocs.sentinel.co")
        }
      },
      style = textStyle,
      modifier = Modifier.clickable {
        context.openWeb("https://docs.sentinel.co/")
      },
    )
    Text(
      text = buildAnnotatedString {
        append(stringResource(R.string.intro_page2_sentinel_site))
        append(" ")
        withStyle(SpanStyle(color = AppColor.Accent, textDecoration = TextDecoration.Underline)) {
          append("Sentinel.co")
        }
      },
      style = textStyle,
      modifier = Modifier.clickable {
        context.openWeb("https://sentinel.co/")
      },
    )
    GradientBorderButton(
      text = stringResource(R.string.common_github),
      gradientBrush = AppColor.MainGradient,
      imageRes = R.drawable.ic_github,
      modifier = Modifier.padding(horizontal = 60.dp),
    ) { context.openWeb("https://github.com/dogwifhatdvpndev/") }
    Image(
      painter = painterResource(R.drawable.img_map),
      contentDescription = null,
      modifier = Modifier.width(200.dp),
    )
    Text(
      text = buildAnnotatedString {
        append(stringResource(R.string.intro_page2_sentinel_map))
        append(" ")
        withStyle(SpanStyle(color = AppColor.Accent, textDecoration = TextDecoration.Underline)) {
          append(stringResource(R.string.intro_page2_node))
        }
      },
      style = textStyle,
      modifier = Modifier.clickable {
        context.openWeb("https://map.sentinel.co/")
      },
    )
    Image(
      painter = painterResource(R.drawable.img_cosmos),
      contentDescription = null,
      modifier = Modifier.width(120.dp),
    )
  }
}

@Composable
private fun TextWithBorder(textRes: Int) {
  Box(
    modifier = Modifier
      .border(1.dp, AppColor.MainGradient, RoundedCornerShape(8.dp)),
  ) {
    Text(
      text = stringResource(textRes),
      color = Color.White,
      fontSize = 16.sp,
      fontWeight = FontWeight.W700,
      modifier = Modifier.padding(vertical = 7.dp, horizontal = 10.dp),
    )
  }
}

@Preview
@Composable
private fun IntroPage1Preview() {
  IntroScreen {}
}
