package com.amin.composeandmaps.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.screens.demo.DemoSpacer
import com.amin.composeandmaps.screens.map_and_cars.defaultPadding
import com.amin.composeandmaps.shared.theme.*

@Composable
fun MenuScreen(
    navigateToMainScreen: () -> Unit,
    navigateToMapScreen: () -> Unit,
    navigateToCarsScreen: () -> Unit,
    navigateToDemoScreen: () -> Unit
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = grey_1),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DemoSpacer()
            MenuButton(text = "Map and Car List", onClick = navigateToMainScreen, colorNumber = 0)
            DemoSpacer()
            MenuButton(text = "Just Map", onClick = navigateToMapScreen, colorNumber = 1)
            DemoSpacer()
            MenuButton(text = "Just Cars", onClick = navigateToCarsScreen, colorNumber = 2)
            DemoSpacer()
            MenuButton(text = "Demo UI elements", onClick = navigateToDemoScreen, colorNumber = 3)
        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(defaultPadding.dp),
            text = "Created by Amin during evenings and weekends 🙄",
            color = green900,
            style = Typography.caption
        )
    }
}

@Composable
fun MenuButton(onClick: () -> Unit, text: String, colorNumber: Int) {
    Button(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(0.70f),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = menuColorLightList[colorNumber]
        )
    ) {
        Text(
            text = text, color = menuColorDarkList[colorNumber]
        )
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        navigateToMainScreen = { },
        navigateToMapScreen = { },
        navigateToCarsScreen = { },
        navigateToDemoScreen = { })
}

