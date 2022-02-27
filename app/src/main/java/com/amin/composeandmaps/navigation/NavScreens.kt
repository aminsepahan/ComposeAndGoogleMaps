package com.amin.composeandmaps.navigation

import android.app.Activity
import androidx.navigation.NavHostController

sealed class NavScreen(val route: String) {
    object SplashScreen : NavScreen("Splash")
    object WelcomeScreen : NavScreen("Welcome")
    object MainScreen : NavScreen("Main")
    object MapScreen : NavScreen("Map")
    object CarsScreen : NavScreen("Cars")
    object MenuScreen : NavScreen("Menu")
    object DemoScreen : NavScreen("Demo")
}

class NavActions(navController: NavHostController, activity: Activity) {

    private val idForClearingBackStack = 0

    val navigateToWelcomeScreenAndClearBackStack: () -> Unit = {
        navController.navigate(route = NavScreen.WelcomeScreen.route) {
            popUpTo(idForClearingBackStack)
        }
    }

    val navigateToMenuScreenAndClearBackStack: () -> Unit = {
        navController.navigate(route = NavScreen.MenuScreen.route) {
            popUpTo(idForClearingBackStack)
        }
    }

    val navigateToMainScreen: () -> Unit = {
        navController.navigate(route = NavScreen.MainScreen.route)
    }

    val navigateToMapScreen: () -> Unit = {
        navController.navigate(route = NavScreen.MapScreen.route)
    }

    val navigateToCarsScreen: () -> Unit = {
        navController.navigate(route = NavScreen.CarsScreen.route)
    }

    val navigateToDemoScreen: () -> Unit = {
        navController.navigate(route = NavScreen.DemoScreen.route)
    }

    val upPress: () -> Unit = {
        activity.onBackPressed()
    }
}

