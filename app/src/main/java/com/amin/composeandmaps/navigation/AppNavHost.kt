package de.allianzservices.hrdmobile.navigation

import android.app.Activity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amin.composeandmaps.ui.screens.SplashScreen
import com.google.accompanist.insets.ProvideWindowInsets


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    val activity = LocalContext.current as Activity
    val actions = remember(navController) {
        NavActions(navController, activity)
    }
    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = NavScreen.SplashScreen.route) {
            composable(NavScreen.SplashScreen.route) {
                SplashScreen(
                    viewModel = hiltViewModel(),
                    navigateToWelcomeScreen = actions.navigateToWelcomeScreenAndClearBackStack
                )
            }

            composable(NavScreen.WelcomeScreen.route) {

            }
        }
    }
}
