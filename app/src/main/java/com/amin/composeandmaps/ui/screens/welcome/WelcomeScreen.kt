package com.amin.composeandmaps.ui.screens.welcome

import android.Manifest
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel,
    navigateToMainScreen: () -> Unit,
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val buttonText = when {
        !locationPermissionsState.allPermissionsGranted -> stringResource(R.string.btn_give_permissions)
        else -> stringResource(id = R.string.welcome_btn_text)
    }
    val okButtonCLicked = {
        when {
            locationPermissionsState.allPermissionsGranted -> navigateToMainScreen()
            else -> locationPermissionsState.launchMultiplePermissionRequest()
        }
    }
    WelcomeScreenContent(okButtonCLicked, buttonText)
}

@Composable
fun WelcomeScreenContent(onOkButtonClick: () -> Unit, buttonText: String) {
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
                text = buttonText,
                style = Typography.button,
                color = white
            )
        }
    }
}

@Preview
@Composable
fun ContentPreview() {
    WelcomeScreenContent(onOkButtonClick = {}, buttonText = "Let's go")
}
