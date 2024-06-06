package com.dogwifhat.app.ui.screens.dashboard

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.sentinel.vpn.based.compose.EffectHandler
import co.sentinel.vpn.based.country_flags.CountryFlag
import co.sentinel.vpn.based.ext.goToGooglePlay
import co.sentinel.vpn.based.state.Status
import co.sentinel.vpn.based.storage.SelectedCity
import co.sentinel.vpn.based.viewModel.dashboard.DashboardScreenEffect as Effect
import co.sentinel.vpn.based.viewModel.dashboard.DashboardScreenState as State
import androidx.compose.foundation.layout.width
import co.sentinel.vpn.based.viewModel.dashboard.DashboardScreenViewModel
import co.sentinel.vpn.based.viewModel.dashboard.VpnStatus
import co.sentinel.vpn.based.vpn.getVpnPermissionRequest
import com.dogwifhat.app.R
import com.dogwifhat.app.common.compose.GradientButton
import com.dogwifhat.app.ui.screens.dashboard.widget.VpnButton
import com.dogwifhat.app.ui.screens.dashboard.widget.VpnButtonState
import com.dogwifhat.app.ui.theme.AppColor
import com.dogwifhat.app.ui.theme.BasedVPNTheme
import com.dogwifhat.app.ui.widget.BasedAlertDialog
import com.dogwifhat.app.ui.widget.ErrorScreen
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
  navigateToCountries: () -> Unit,
  navigateToSettings: () -> Unit,
  showAd: suspend () -> Boolean,
) {
  val viewModel = hiltViewModel<DashboardScreenViewModel>()
  val state by viewModel.stateHolder.state.collectAsState()

  val context = LocalContext.current

  val scope = rememberCoroutineScope()

  val vpnPermissionRequest = rememberLauncherForActivityResult(
    ActivityResultContracts.StartActivityForResult(),
  ) { result ->
    viewModel.onPermissionsResult(result.resultCode == Activity.RESULT_OK)
  }

  EffectHandler(viewModel.stateHolder.effects) { effect ->
    when (effect) {
      is Effect.ShowAd -> scope.launch {
        showAd()
        viewModel.onAdShown()
      }

      is Effect.ShowSelectServer -> navigateToCountries()

      is Effect.CheckVpnPermission -> {
        val intent = getVpnPermissionRequest(context)
        if (intent != null) {
          vpnPermissionRequest.launch(intent)
        } else {
          viewModel.onPermissionsResult(true)
        }
      }

      is Effect.ShowSettings -> navigateToSettings()

      is Effect.ShowGooglePlay -> context.goToGooglePlay()

      is Effect.ChangeMapPosition -> Unit
    }
  }

  DashboardScreenStateless(
    state = state,
    onConnectClick = viewModel::onConnectClick,
    onQuickConnectClick = viewModel::onQuickConnectClick,
    onSelectServerClick = viewModel::onSelectServerClick,
    onSettingsClick = viewModel::onSettingsClick,
    onTryAgainClick = viewModel::onTryAgainClick,
    onUpdateClick = viewModel::onUpdateClick,
    onAlertConfirmClick = viewModel::onAlertConfirmClick,
    onAlertDismissRequest = viewModel::onAlertDismissRequest,
  )
}

@Composable
fun DashboardScreenStateless(
  state: State,
  onConnectClick: () -> Unit,
  onQuickConnectClick: () -> Unit,
  onSelectServerClick: () -> Unit,
  onSettingsClick: () -> Unit,
  onTryAgainClick: () -> Unit,
  onUpdateClick: () -> Unit,
  onAlertConfirmClick: () -> Unit,
  onAlertDismissRequest: () -> Unit,
) {
  when {
    state.isOutdated -> ErrorScreen(
      title = stringResource(R.string.update_required_title),
      description = stringResource(R.string.update_required_description),
      buttonLabel = stringResource(R.string.update_required_button),
      iconResId = R.drawable.ic_update,
      onButtonClick = onUpdateClick,
    )

    state.isBanned -> ErrorScreen(
      title = null,
      description = stringResource(R.string.error_banned_title),
      onButtonClick = null,
      imageResId = R.drawable.img_settings,
    )

    state.status is Status.Error -> ErrorScreen(
      isLoading = (state.status as Status.Error).isLoading,
      onButtonClick = onTryAgainClick,
      imageResId = R.drawable.img_settings,
    )

    else -> Content(
      state = state,
      onConnectClick = onConnectClick,
      onQuickConnectClick = onQuickConnectClick,
      onSelectServerClick = onSelectServerClick,
      onSettingsClick = onSettingsClick,
      onAlertConfirmClick = onAlertConfirmClick,
      onAlertDismissRequest = onAlertDismissRequest,
    )
  }
}

@Composable
private fun Content(
  state: State,
  onConnectClick: () -> Unit,
  onQuickConnectClick: () -> Unit,
  onSelectServerClick: () -> Unit,
  onSettingsClick: () -> Unit,
  onAlertConfirmClick: () -> Unit,
  onAlertDismissRequest: () -> Unit = {},
) {
  Box(
    modifier = Modifier
      .background(AppColor.BackGradient)
      .fillMaxSize(),
  ) {
    Box {
      BackgroundImages(
        isConnected = state.vpnStatus == VpnStatus.Connected,
      )
      TopBar(
        state = state,
        onSettingsClick = onSettingsClick,
      )
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.align(Alignment.Center),
      ) {
        Spacer(modifier = Modifier.size(70.dp))
        VpnButton(
          state = state.vpnStatus.toButtonState(),
          onClick = onConnectClick,
        )
        Spacer(modifier = Modifier.size(14.dp))
        StatusBlock(state = state, onClick = onConnectClick)
      }
      BottomBar(
        state = state,
        onSelectServerClick = onSelectServerClick,
        onQuickConnectClick = onQuickConnectClick,
        onDisconnectClick = onConnectClick,
        modifier = Modifier.align(Alignment.BottomCenter),
      )
    }
    if (state.status is Status.Loading) {
      LoadingOverlay()
    }
    if (state.isErrorAlertVisible) {
      BasedAlertDialog(
        title = stringResource(R.string.dashboard_error_connection_title),
        description = stringResource(R.string.dashboard_error_connection_description),
        onConfirmClick = onAlertConfirmClick,
        onDismissRequest = onAlertDismissRequest,
      )
    }
  }
}

@Composable
private fun BackgroundImages(isConnected: Boolean) {
  Box(
    modifier = Modifier.fillMaxSize(),
  ) {
    if (isConnected) {
      Image(
        painter = painterResource(R.drawable.img_main_left),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
          .padding(top = 60.dp)
          .fillMaxWidth(0.5f)
          .align(Alignment.TopStart),
      )
      Image(
        painter = painterResource(R.drawable.img_main_right),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
          .padding(top = 50.dp)
          .fillMaxWidth(0.62f)
          .align(Alignment.TopEnd),
      )
    } else {
      Image(
        painter = painterResource(R.drawable.img_wow),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .padding(top = 130.dp)
          .fillMaxWidth(0.65f)
          .align(Alignment.TopCenter),
      )
    }
  }
}

@Composable
private fun TopBar(
  state: State,
  onSettingsClick: () -> Unit,
) {
  Box(
    modifier = Modifier
      .statusBarsPadding()
      .padding(horizontal = 20.dp)
      .padding(top = 20.dp, bottom = 32.dp)
      .fillMaxWidth(),
  ) {
    IconButton(
      onClick = onSettingsClick,
      modifier = Modifier
        .size(34.dp)
        .align(Alignment.TopEnd),
    ) {
      Icon(
        painter = painterResource(R.drawable.ic_settings),
        contentDescription = stringResource(R.string.dashboard_menu_settings),
        modifier = Modifier.size(18.dp),
        tint = Color.White,
      )
    }
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.fillMaxWidth(),
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
          painter = painterResource(R.drawable.img_small),
          contentDescription = null,
          modifier = Modifier.size(34.dp),
        )
        Spacer(modifier = Modifier.size(10.dp))
        Column {
          Text(
            text = stringResource(R.string.app_name),
            color = Color.White,
            fontWeight = FontWeight.W500,
            fontSize = 20.sp,
          )
          Image(
            painter = painterResource(R.drawable.img_sentinel),
            contentDescription = null,
            modifier = Modifier
              .width(80.dp)
              .align(Alignment.End),
          )
        }
      }
      if (state.vpnStatus == VpnStatus.Connected) {
        Spacer(modifier = Modifier.size(28.dp))
        Text(
          text = stringResource(R.string.dashboard_your_ip, state.ipAddress),
          color = Color(0xFFB9BCC0),
          fontSize = 14.sp,
          fontWeight = FontWeight.W300,
        )
      }
    }
  }
}

@Composable
private fun StatusBlock(
  state: State,
  onClick: () -> Unit,
) {
  val stateLabelRes = when (state.vpnStatus) {
    is VpnStatus.Connected -> R.string.dashboard_state_connected
    is VpnStatus.Connecting -> R.string.dashboard_state_connecting
    is VpnStatus.Disconnected -> R.string.dashboard_state_disconnected
    is VpnStatus.Disconnecting -> R.string.dashboard_state_disconnecting
  }
  Text(
    text = stringResource(stateLabelRes),
    color = Color.White,
    fontWeight = FontWeight.W600,
    fontSize = 18.sp,
    modifier = Modifier.clickable(onClick = onClick),
  )
  Spacer(Modifier.size(4.dp))
  val infoText = when (state.vpnStatus) {
    is VpnStatus.Connecting -> stringResource(R.string.dashboard_state_connecting_info)
    else -> ""
  }
  Text(
    text = infoText,
    color = Color(0xFFAEAEB2),
    fontSize = 14.sp,
  )
}

@Composable
fun BottomBar(
  state: State,
  onQuickConnectClick: () -> Unit,
  onDisconnectClick: () -> Unit,
  onSelectServerClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 45.dp)
      .padding(top = 24.dp, bottom = 24.dp)
      .navigationBarsPadding(),
  ) {

    val selectedCity = state.selectedCity
    if (selectedCity != null) {
      SelectedCountryRow(
        selectedCity = selectedCity,
        onClick = onSelectServerClick,
        isEnabled = state.status != Status.Loading,
      )
      Spacer(modifier = Modifier.size(16.dp))
    }
    if (state.vpnStatus is VpnStatus.Connected ||
      state.vpnStatus is VpnStatus.Connecting
    ) {
      DisconnectButton(onDisconnectClick)
    } else {
      QuickConnectButton(onQuickConnectClick)
    }
  }
}

@Composable
fun SelectedCountryRow(
  selectedCity: SelectedCity,
  onClick: () -> Unit,
  isEnabled: Boolean,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .clickable(isEnabled, onClick = onClick)
      .border(0.5.dp, AppColor.MainGradient, RoundedCornerShape(8.dp))
      .height(48.dp)
      .padding(horizontal = 16.dp)
      .fillMaxWidth(),
  ) {
    val flagRes = selectedCity.countryFlag?.res
    if (flagRes != null) {
      Image(
        painter = painterResource(flagRes),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape),
      )
    }
    Spacer(modifier = Modifier.size(16.dp))
    Text(
      text = selectedCity.countryName,
      color = Color.White,
      fontWeight = FontWeight.W500,
      fontSize = 16.sp,
    )
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

@Composable
private fun QuickConnectButton(onClick: () -> Unit) {
  GradientButton(
    onClick = onClick,
    brush = AppColor.MainGradient,
  ) {
    Image(
      painter = painterResource(R.drawable.img_small),
      contentDescription = null,
      modifier = Modifier.size(26.dp),
    )
    Spacer(modifier = Modifier.size(6.dp))
    Text(
      text = stringResource(R.string.dashboard_quick_connect),
      fontSize = 16.sp,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
    )
  }
}

@Composable
private fun DisconnectButton(onClick: () -> Unit) {
  GradientButton(
    onClick = onClick,
    brush = AppColor.AlertGradient,
  ) {
    Image(
      painter = painterResource(R.drawable.img_small),
      contentDescription = null,
      modifier = Modifier.size(26.dp),
    )
    Spacer(modifier = Modifier.size(6.dp))
    Text(
      text = stringResource(R.string.dashboard_disconnect_from_vpn),
      fontSize = 16.sp,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
    )
  }
}

@Composable
private fun LoadingOverlay() {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = {},
      )
      .navigationBarsPadding()
      .background(Color.Black.copy(alpha = 0.5f))
      .fillMaxSize(),
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      CircularProgressIndicator(
        color = Color.White,
      )
      Spacer(modifier = Modifier.size(24.dp))
      Text(
        text = stringResource(R.string.dashboard_loading),
        fontSize = 16.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 48.dp),
      )
    }
  }
}

fun VpnStatus.toButtonState(): VpnButtonState = when (this) {
  is VpnStatus.Connected -> VpnButtonState.Connected
  is VpnStatus.Connecting -> VpnButtonState.Connecting
  is VpnStatus.Disconnected -> VpnButtonState.Disconnected
  is VpnStatus.Disconnecting -> VpnButtonState.Disconnecting
}

@Preview
@Composable
fun DashboardScreenPreview() {
  BasedVPNTheme {
    DashboardScreenStateless(
      state = State(
        selectedCity = SelectedCity(
          id = 0,
          name = "Buenos Aires",
          countryId = 0,
          countryName = "Argentina",
          countryFlag = CountryFlag.AR,
        ),
        ipAddress = "91.208.132.23",
      ),
      onConnectClick = {},
      onQuickConnectClick = {},
      onSelectServerClick = {},
      onSettingsClick = {},
      onTryAgainClick = {},
      onUpdateClick = {},
      onAlertConfirmClick = {},
      onAlertDismissRequest = {},
    )
  }
}
