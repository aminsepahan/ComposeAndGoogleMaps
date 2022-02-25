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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.R
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
            text = stringResource(R.string.welcome_text),
            style = Typography.h5,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = onOkButtonClick, modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(id = R.string.welcome_btn_text),
                style = Typography.button,
                color = white
            )
        }
    }
}

@Preview
@Composable
fun ContentPreview() {
    WelcomeScreenContent(onOkButtonClick = {})
}