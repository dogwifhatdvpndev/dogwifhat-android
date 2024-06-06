package com.dogwifhat.app.ui.screens.countries

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.sentinel.vpn.based.compose.EffectHandler
import co.sentinel.vpn.based.network.model.Country
import co.sentinel.vpn.based.state.Status
import co.sentinel.vpn.based.viewModel.countries.CountriesScreenEffect as Effect
import co.sentinel.vpn.based.viewModel.countries.CountriesScreenState as State
import co.sentinel.vpn.based.viewModel.countries.CountriesScreenViewModel
import com.dogwifhat.app.R
import com.dogwifhat.app.ui.theme.AppColor
import com.dogwifhat.app.ui.widget.BaseRow
import com.dogwifhat.app.ui.widget.ErrorScreen
import com.dogwifhat.app.ui.widget.TopBar

@Composable
fun CountriesScreen(
  navigateBack: () -> Unit,
  navigateToCities: (Country) -> Unit,
) {
  val viewModel = hiltViewModel<CountriesScreenViewModel>()
  val state by viewModel.stateHolder.state.collectAsState()

  EffectHandler(viewModel.stateHolder.effects) { effect ->
    when (effect) {
      is Effect.ShowCitiesScreen ->
        navigateToCities(effect.country)
    }
  }

  CountriesScreenStateless(
    state = state,
    navigateBack = navigateBack,
    onItemClick = viewModel::onCountryClick,
    onTryAgainClick = viewModel::onTryAgainClick,
  )
}

@Composable
fun CountriesScreenStateless(
  state: State,
  navigateBack: () -> Unit,
  onItemClick: (Country) -> Unit,
  onTryAgainClick: () -> Unit,
) {
  Scaffold(
    containerColor = Color.Transparent,
    topBar = {
      TopBar(
        title = stringResource(R.string.countries_title),
        navigateBack = navigateBack,
      )
    },
    content = { paddingValues ->
      Content(
        paddingValues = paddingValues,
        state = state,
        onItemClick = onItemClick,
        onTryAgainClick = onTryAgainClick,
      )
    },
    modifier = Modifier.background(AppColor.BackGradient),
  )
}

@Composable
fun Content(
  paddingValues: PaddingValues,
  state: State,
  onItemClick: (Country) -> Unit,
  onTryAgainClick: () -> Unit,
) {
  Box(
    modifier = Modifier
      .padding(paddingValues)
      .fillMaxSize(),
  ) {
    when (state.status) {
      is Status.Loading -> Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
      ) {
        CircularProgressIndicator()
      }

      is Status.Error -> ErrorScreen(
        isLoading = (state.status as Status.Error).isLoading,
        onButtonClick = onTryAgainClick,
        imageResId = R.drawable.img_settings,
      )

      is Status.Data -> Data(state, onItemClick)
    }
  }
}

@Composable
private fun BoxScope.Data(
  state: State,
  onItemClick: (Country) -> Unit,
) {
  Image(
    painter = painterResource(R.drawable.img_countries),
    contentDescription = null,
    contentScale = ContentScale.Fit,
    modifier = Modifier
      .fillMaxWidth(0.7f)
      .align(Alignment.BottomStart),
  )
  LazyColumn(
    contentPadding = PaddingValues(
      horizontal = 32.dp,
      vertical = 16.dp,
    ),
    modifier = Modifier.fillMaxSize(),
  ) {
    items(state.countries) { country ->
      Column {
        BaseRow(
          title = country.name,
          imageRes = country.flag?.res,
          onClick = { onItemClick(country) },
        )
        Divider(color = Color(0xFF252B34))
      }
    }
  }
}
