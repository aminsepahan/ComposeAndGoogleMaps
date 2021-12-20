package com.amin.composeandmaps.navigation

import android.app.Activity
import androidx.navigation.NavHostController

sealed class NavScreen(val route: String) {
    object SplashScreen : NavScreen("Splash")
    object WelcomeScreen : NavScreen("Welcome")
    object MainScreen : NavScreen("Main")
}

class NavActions(navController: NavHostController, activity: Activity) {

    private val idForClearingBackStack = 0

    val navigateToWelcomeScreenAndClearBackStack: () -> Unit = {
        navController.navigate(route = NavScreen.WelcomeScreen.route) {
            popUpTo(idForClearingBackStack)
        }
    }

    val navigateToMainScreenAndClearBackStack: () -> Unit = {
        navController.navigate(route = NavScreen.MainScreen.route) {
            popUpTo(idForClearingBackStack)
        }
    }

    val upPress: () -> Unit = {
        activity.onBackPressed()
    }
}

