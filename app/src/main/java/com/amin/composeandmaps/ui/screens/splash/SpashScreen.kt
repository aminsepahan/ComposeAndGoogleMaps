package com.amin.composeandmaps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.R
import com.amin.composeandmaps.navigation.NavScreen
import com.amin.composeandmaps.ui.screens.splash.SplashViewModel
import com.amin.composeandmaps.ui.theme.Typography
import com.amin.composeandmaps.ui.theme.screenBack
import com.amin.composeandmaps.utils.SelfDestructEvent

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
        Text(
            modifier = Modifier.padding(50.dp),
            style = Typography.h4,
            text = stringResource(R.string.splash_text)
        )
    }
}

@Preview
@Composable
fun ContentPreview() {
    SplashScreenContent()
}