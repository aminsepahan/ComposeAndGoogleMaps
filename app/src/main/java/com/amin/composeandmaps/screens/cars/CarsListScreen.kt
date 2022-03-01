package com.amin.composeandmaps.screens.cars

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.shared.util.OperationState

@Composable
fun CarListScreen(
    cars: List<Car>,
    onItemClicked: (item: Car) -> Unit,
    navigateToMapScreen: () -> Unit
) {
    CarList(cars = cars, onItemClicked = onItemClicked)
}


@Preview
@Composable
fun CarListPreview() {
    Column() {
        for (i in 1..3) {
            ListItem(item = Car.mock(), onCarCardClicked = {})
        }
    }
}
