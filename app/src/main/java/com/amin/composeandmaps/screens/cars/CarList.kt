package com.amin.composeandmaps.screens.cars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.screens.map_and_cars.ImageItem
import com.amin.composeandmaps.shared.theme.AppCard
import com.amin.composeandmaps.shared.util.UIState

@Composable
fun CarList(
    state: UIState<List<Car>>,
    onItemClicked: (item: Car) -> Unit
) {
    LazyColumn {
        item {
            state.data?.forEach { item ->
                ListItem(item) {
                    onItemClicked(item)
                }
            }
        }
    }
}


@Composable
fun ListItem(item: Car, onCarCardClicked: () -> Unit) {
    AppCard(
        modifier = Modifier.padding(5.dp),
        onClick = onCarCardClicked
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageItem(imageUrl = item.carImageUrl)
            Text(text = "${item.name}, ${item.modelName}")
        }
    }
}
