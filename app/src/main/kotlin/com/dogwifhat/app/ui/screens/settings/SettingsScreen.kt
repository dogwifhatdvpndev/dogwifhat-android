package com.dogwifhat.app.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.sentinel.vpn.based.compose.EffectHandler
import co.sentinel.vpn.based.ext.openWeb
import co.sentinel.vpn.based.network.model.Protocol
import co.sentinel.vpn.based.viewModel.settings.SettingsScreenEffect
import co.sentinel.vpn.based.viewModel.settings.SettingsScreenState as State
import co.sentinel.vpn.based.viewModel.settings.SettingsScreenViewModel
import co.sentinel.vpn.based.vpn.DdsConfigurator
import com.dogwifhat.app.R
import com.dogwifhat.app.common.compose.GradientBorderButton
import com.dogwifhat.app.ui.screens.settings.widgets.DnsDialog
import com.dogwifhat.app.ui.screens.settings.widgets.ProtocolDialog
import com.dogwifhat.app.ui.theme.AppColor
import com.dogwifhat.app.ui.widget.BaseRow
import com.dogwifhat.app.ui.widget.TopBar
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navigateBack: () -> Unit) {

  val viewModel = hiltViewModel<SettingsScreenViewModel>()
  val state by viewModel.stateHolder.state.collectAsState()

  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  EffectHandler(viewModel.stateHolder.effects) { effect ->
    when (effect) {
      is SettingsScreenEffect.OpenTelegram -> Unit

      is SettingsScreenEffect.CopyLogsToClipboard -> {
        clipboardManager.setText(AnnotatedString(effect.logs))
        scope.launch {
          snackbarHostState.showSnackbar(
            context.getString(R.string.settings_logs_success),
          )
        }
      }
    }
  }

  SettingsScreenStateless(
    state = state,
    snackbarHostState = snackbarHostState,
    navigateBack = navigateBack,
    onDnsRowClick = viewModel::onDnsRowClick,
    onDnsDialogConfirmClick = viewModel::onDnsSelected,
    onDnsDialogDismissClick = viewModel::onDnsDialogDismissClick,
    onTelegramClick = viewModel::onTelegramClick,
    onProtocolRowClick = viewModel::onProtocolRowClick,
    onProtocolDialogConfirmClick = viewModel::onProtocolSelected,
    onProtocolDialogDismissClick = viewModel::onProtocolDialogDismissClick,
    onLogsRowClick = viewModel::onLogsRowClick,
  )
}

@Composable
fun SettingsScreenStateless(
  state: State,
  snackbarHostState: SnackbarHostState,
  navigateBack: () -> Unit,
  onDnsRowClick: () -> Unit,
  onDnsDialogConfirmClick: (DdsConfigurator.Dns) -> Unit,
  onDnsDialogDismissClick: () -> Unit,
  onProtocolRowClick: () -> Unit,
  onProtocolDialogConfirmClick: (Protocol) -> Unit,
  onProtocolDialogDismissClick: () -> Unit,
  onLogsRowClick: () -> Unit,
  onTelegramClick: () -> Unit,
) {
  Scaffold(
    containerColor = Color.Transparent,
    topBar = {
      TopBar(
        title = stringResource(R.string.settings_title),
        navigateBack = navigateBack,
      )
    },
    snackbarHost = { SnackbarHost(snackbarHostState) },
    content = { paddingValues ->
      Content(
        paddingValues = paddingValues,
        state = state,
        onDnsRowClick = onDnsRowClick,
        onDnsDialogConfirmClick = onDnsDialogConfirmClick,
        onDnsDialogDismissClick = onDnsDialogDismissClick,
        onProtocolRowClick = onProtocolRowClick,
        onProtocolDialogConfirmClick = onProtocolDialogConfirmClick,
        onProtocolDialogDismissClick = onProtocolDialogDismissClick,
        onLogsRowClick = onLogsRowClick,
        onTelegramClick = onTelegramClick,
      )
    },
    modifier = Modifier.background(AppColor.BackGradient),
  )
}

@Composable
fun Content(
  paddingValues: PaddingValues,
  state: State,
  onDnsRowClick: () -> Unit,
  onDnsDialogConfirmClick: (DdsConfigurator.Dns) -> Unit,
  onDnsDialogDismissClick: () -> Unit,
  onProtocolRowClick: () -> Unit,
  onProtocolDialogConfirmClick: (Protocol) -> Unit,
  onProtocolDialogDismissClick: () -> Unit,
  onLogsRowClick: () -> Unit,
  onTelegramClick: () -> Unit,
) {
  Box(Modifier.padding(paddingValues)) {
    Image(
      painter = painterResource(R.drawable.img_settings),
      contentDescription = null,
      contentScale = ContentScale.Fit,
      modifier = Modifier
        .fillMaxWidth(0.5f)
        .align(Alignment.BottomEnd),
    )
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(10.dp),
      contentPadding = PaddingValues(
        horizontal = 32.dp,
        vertical = 16.dp,
      ),
      modifier = Modifier.fillMaxSize(),
    ) {
      item {
        BaseRow(
          title = stringResource(R.string.settings_row_dns),
          subtitle = state.currentDns
            ?.let { stringResource(it.getLabelRes()) } ?: "",
          onClick = onDnsRowClick,
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF282F39)),
        )
      }
      item {
        BaseRow(
          title = stringResource(R.string.settings_row_protocol),
          subtitle = state.currentProtocol?.labelRes
            ?.let { stringResource(it) } ?: "",
          onClick = onProtocolRowClick,
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF282F39)),
        )
      }
      item {
        BaseRow(
          title = stringResource(R.string.settings_row_logs),
          subtitle = stringResource(R.string.settings_logs_description),
          onClick = onLogsRowClick,
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF282F39)),
        )
      }
      item {
        val context = LocalContext.current
        Spacer(modifier = Modifier.size(20.dp))
        BaseRow(
          title = stringResource(R.string.settings_privacy_policy),
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF282F39)),
        ) {
          context.openWeb("https://dogwifhatdvpn.com/legal/privacy/")
        }
      }
      item {
        val context = LocalContext.current
        BaseRow(
          title = stringResource(R.string.settings_terms_of_service),
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF282F39)),
        ) {
          context.openWeb("https://dogwifhatdvpn.com/legal/terms/")
        }
      }
      item {
        SocialNetworks()
      }
    }
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(bottom = 16.dp)
        .align(Alignment.BottomCenter),
    ) {
      Text(
        text = stringResource(R.string.settings_app_version, state.appVersion),
        fontSize = 16.sp,
        color = Color(0xFFAEAEB2),
      )
    }
  }
  if (state.isDnsSelectorVisible) {
    DnsDialog(
      state = state,
      onConfirmClick = onDnsDialogConfirmClick,
      onDismissClick = onDnsDialogDismissClick,
      onDismissRequest = onDnsDialogDismissClick,
    )
  }

  if (state.isProtocolSelectorVisible) {
    ProtocolDialog(
      state = state,
      onConfirmClick = onProtocolDialogConfirmClick,
      onDismissClick = onProtocolDialogDismissClick,
      onDismissRequest = onProtocolDialogDismissClick,
    )
  }
}

@Composable
private fun SocialNetworks() {
  val context = LocalContext.current
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 40.dp),
  ) {
    GradientBorderButton(
      text = stringResource(R.string.common_github),
      gradientBrush = AppColor.MainGradient,
      imageRes = R.drawable.ic_github,
      modifier = Modifier.padding(horizontal = 20.dp),
    ) {
      context.openWeb("https://github.com/dogwifhatdvpndev/")
    }
    Spacer(modifier = Modifier.size(12.dp))
    GradientBorderButton(
      text = stringResource(R.string.common_telegram),
      gradientBrush = Brush.horizontalGradient(
        listOf(Color(0xFF34AADF), Color(0xFF34AADF)),
      ),
      imageRes = R.drawable.ic_telegram,
      modifier = Modifier.padding(horizontal = 20.dp),
    ) {
      context.openWeb("https://t.me/dogwifhatdvpn/")
    }
  }
}

fun DdsConfigurator.Dns.getLabelRes() = when (this) {
  DdsConfigurator.Dns.Cloudflare -> R.string.settings_dns_cloudflare
  DdsConfigurator.Dns.Google -> R.string.settings_dns_google
  DdsConfigurator.Dns.Handshake -> R.string.settings_dns_handshake
}
