package com.amin.composeandmaps.screens.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.screens.cars.CoordinateTextField
import com.amin.composeandmaps.screens.cars.ListItem
import com.amin.composeandmaps.screens.map_and_cars.TopActionBarHiddenPreview
import com.amin.composeandmaps.screens.menu.MenuButton

@Preview
@Composable
fun DemoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        DemoSpacer()
        MenuButton(text = "Demo UI elements", onClick = { }, colorNumber = 3)
        DemoSpacer()
        TopActionBarHiddenPreview()
        DemoSpacer()
        ListItem(item = Car.mock()) {

        }
        DemoSpacer()
        CoordinateTextField(title = "northeast", text = "53.34613", onTextChange = {}) {

        }
    }
}

@Composable
fun DemoSpacer(size: Dp = 10.dp) {
    Spacer(modifier = Modifier.size(size))
}
