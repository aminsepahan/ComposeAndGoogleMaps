package de.allianzservices.hrdmobile.navigation

import android.app.Activity
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class NavScreen(val route: String) {
    object SplashScreen : NavScreen("Splash")
    object WelcomeScreen : NavScreen("Welcome")
}

class NavActions(navController: NavHostController, activity: Activity) {

    val idForClearingBackStack = 0

    val navigateToWelcomeScreenAndClearBackStack: () -> Unit = {
        navController.navigate(route = NavScreen.WelcomeScreen.route) {
            popUpTo(idForClearingBackStack)
        }
    }

    val upPress: () -> Unit = {
        activity.onBackPressed()
    }
}

