package com.amin.composeandmaps.screens.cars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.shared.util.UIState
import com.amin.composeandmaps.shared.util.success

@Composable
fun CarListScreen(
    state: UIState<List<Car>>,
    onItemClicked: (item: Car) -> Unit,
    navigateToMapScreen: () -> Unit
) {
    CarList(state = state, onItemClicked = onItemClicked)
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
