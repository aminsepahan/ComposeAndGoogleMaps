package com.amin.composeandmaps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.R
import com.amin.composeandmaps.ui.theme.screenBack
import de.allianzservices.hrdmobile.navigation.NavScreen
import de.allianzservices.hrdmobile.ui.splash.SplashViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navigateToWelcomeScreen: () -> Unit,
) {
    SplashScreenUI()
}

@Composable
fun SplashScreenUI() {
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