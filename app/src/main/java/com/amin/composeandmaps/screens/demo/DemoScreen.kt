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
import com.amin.composeandmaps.screens.cars.CarListPreview
import com.amin.composeandmaps.screens.map_and_cars.TopActionBarHiddenPreview

@Preview
@Composable
fun DemoScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)
        .padding(horizontal = 10.dp)
        .verticalScroll(rememberScrollState())) {
        DemoSpacer()
        TopActionBarHiddenPreview()
        DemoSpacer()
        CarListPreview()
    }
}

@Composable
fun DemoSpacer(size: Dp = 10.dp){
    Spacer(modifier = Modifier.size(size))
}
