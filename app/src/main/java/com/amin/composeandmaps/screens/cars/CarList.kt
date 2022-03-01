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
import com.amin.composeandmaps.R
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.data.models.FleetType
import com.amin.composeandmaps.screens.map_and_cars.ImageItem
import com.amin.composeandmaps.shared.theme.AppCard
import com.amin.composeandmaps.shared.util.defaultPadding

@Composable
fun CarList(
    cars: List<Car>,
    onItemClicked: (item: Car) -> Unit
) {
    LazyColumn {
        item {
            cars.forEach { item ->
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
            ImageItem(
                imageResourceId = when (item.fleetType) {
                    FleetType.POOLING -> R.drawable.pooling
                    FleetType.TAXI -> R.drawable.taxi
                }
            )
            Text(
                modifier = Modifier.padding(start = defaultPadding.dp),
                text = "${item.fleetType.title}, Heading: ${item.heading.toFloat()}"
            )
        }
    }
}
