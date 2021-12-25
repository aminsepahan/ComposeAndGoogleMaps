package com.amin.composeandmaps.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.ui.theme.Typography
import com.amin.composeandmaps.ui.theme.screenBack
import com.amin.composeandmaps.ui.theme.white

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel,
    navigateToMainScreen: () -> Unit,
) {
    WelcomeScreenContent(onOkButtonClick = navigateToMainScreen)
}

@Composable
fun WelcomeScreenContent(onOkButtonClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(screenBack),
    ) {
        Text(
            text = "Your welcome text here :)",
            style = Typography.h2,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = onOkButtonClick, modifier = Modifier.padding(16.dp)) {
            Text("Ok :p", style = Typography.button, color = white)
        }
    }
}

@Preview
@Composable
fun ContentPreview() {
    WelcomeScreenContent(onOkButtonClick = {})
}