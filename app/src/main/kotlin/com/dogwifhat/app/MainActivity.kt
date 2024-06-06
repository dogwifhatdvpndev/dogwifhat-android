package com.dogwifhat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.sentinel.vpn.based.storage.BasedStorage
import com.dogwifhat.app.ui.screens.cities.CitiesScreen
import com.dogwifhat.app.ui.screens.countries.CountriesScreen
import com.dogwifhat.app.ui.screens.dashboard.DashboardScreen
import com.dogwifhat.app.ui.screens.intro.IntroScreen
import com.dogwifhat.app.ui.screens.settings.SettingsScreen
import com.dogwifhat.app.ui.theme.BasedVPNTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var basedStorage: BasedStorage

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setFullScreen()
    setContent {
      BasedVPNTheme {
        val navController = rememberNavController()
        NavHost(
          navController = navController,
          startDestination = when {
            basedStorage.isOnboardingShown().not() -> Destination.Intro
            else -> Destination.Dashboard
          },
          enterTransition = { EnterTransition.None },
          exitTransition = { ExitTransition.None },
        ) {
          composable(Destination.Intro) {
            LaunchedEffect(Unit) {
              basedStorage.onOnboardingShown()
            }
            IntroScreen(
              navigateToDashboard = {
                navController.popBackStack()
                navController.navigate(Destination.Dashboard)
              },
            )
          }
          composable(Destination.Dashboard) {
            DashboardScreen(
              navigateToCountries = { navController.navigate(Destination.Countries) },
              navigateToSettings = { navController.navigate(Destination.Settings) },
              showAd = { true },
            )
          }
          composable(Destination.Countries) {
            CountriesScreen(
              navigateBack = { navController.popBackStack() },
              navigateToCities = { country ->
                navController.navigate("countries/${country.id}/cities")
              },
            )
          }
          composable(
            route = Destination.Cities,
            arguments = listOf(navArgument(Args.CountryId) { type = NavType.IntType }),
          ) { backStackEntry ->
            CitiesScreen(
              countryId = backStackEntry.arguments?.getInt(Args.CountryId),
              navigateBack = { navController.popBackStack() },
              navigateBackToRoot = {
                navController.popBackStack(
                  route = Destination.Dashboard,
                  inclusive = false,
                )
              },
            )
          }
          composable(Destination.Settings) {
            SettingsScreen(
              navigateBack = { navController.popBackStack() },
            )
          }
        }
      }
    }
  }
}

fun ComponentActivity.setFullScreen() {
  WindowCompat.setDecorFitsSystemWindows(window, false)
}

object Destination {
  const val Intro = "intro"
  const val Dashboard = "dashboard"
  const val Countries = "countries"
  const val Cities = "countries/{${Args.CountryId}}/cities"
  const val Settings = "settings"
}

object Args {
  const val CountryId = "countryId"
}
