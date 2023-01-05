package com.amin.composeandmaps.navigation

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
import com.amin.composeandmaps.navigation.NavScreen.*
import com.amin.composeandmaps.screens.cars.CarListScreen
import com.amin.composeandmaps.screens.demo.DemoScreen
import com.amin.composeandmaps.screens.map.MapScreen
import com.amin.composeandmaps.screens.splash.SplashScreen
import com.amin.composeandmaps.screens.map_and_cars.MainScreen
import com.amin.composeandmaps.screens.menu.MenuScreen
import com.amin.composeandmaps.screens.welcome.WelcomeScreen
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
        NavHost(navController = navController, startDestination = SplashScreen.route) {
            composable(SplashScreen.route) {
                SplashScreen(
                    viewModel = hiltViewModel(),
                    navigateToWelcomeScreen = actions.navigateToWelcomeScreenAndClearBackStack
                )
            }
            composable(WelcomeScreen.route) {
                WelcomeScreen(
                    viewModel = hiltViewModel(),
                    navigateToMenuScreen = actions.navigateToMenuScreenAndClearBackStack
                )
            }
            composable(MainScreen.route) {
                MainScreen(
                    viewModel = hiltViewModel(),
                )
            }
            composable(MapScreen.route) {
                MapScreen(
                    viewModel = hiltViewModel(),
                )
            }
            composable(CarsScreen.route) {
                CarListScreen(
                    viewModel = hiltViewModel(),
                )
            }
            composable(DemoScreen.route) {
                DemoScreen()
            }
            composable(MenuScreen.route) {
                MenuScreen(
                    navigateToMainScreen = actions.navigateToMainScreen,
                    navigateToCarsScreen = actions.navigateToCarsScreen,
                    navigateToMapScreen = actions.navigateToMapScreen,
                    navigateToDemoScreen = actions.navigateToDemoScreen,
                )
            }
        }
    }
}
