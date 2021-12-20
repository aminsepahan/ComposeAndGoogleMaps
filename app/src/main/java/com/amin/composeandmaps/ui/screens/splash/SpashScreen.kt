package com.amin.composeandmaps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amin.composeandmaps.ui.theme.screenBack
import com.amin.composeandmaps.utils.SelfDestructEvent
import com.amin.composeandmaps.navigation.NavScreen
import de.allianzservices.hrdmobile.ui.splash.SplashViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navigateToWelcomeScreen: () -> Unit,
) {
    SelfDestructEvent(viewModel.startAppNavigation) {
        when (it) {
            NavScreen.WelcomeScreen.route -> run(navigateToWelcomeScreen)
        }
    }
    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(screenBack),
    ) {
        Text("Your logo here :)")
    }
}

@Preview
@Composable
fun ContentPreview() {
    SplashScreenContent()
}