package com.amin.composeandmaps.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.amin.composeandmaps.screens.map_and_cars.TopActionBarButton
import com.amin.composeandmaps.shared.util.rememberMapViewWithLifecycle
import com.google.android.libraries.maps.model.LatLngBounds

@Composable
fun MapScreen(
    viewModel: MapViewModel,
) {
    val state by viewModel.state.collectAsState()
    var currentCameraBounds: LatLngBounds? by remember {
        mutableStateOf(null)
    }
    MapScreenContent(
        state = state,
        onCameraChanged = { newBounds ->
            currentCameraBounds = newBounds
            viewModel.onCameraBoundsChanged(newBounds)
        },
        onSearchButtonClicked = {
            currentCameraBounds?.let {
                viewModel.searchWithNewBounds(it)
            }
        },
    )
}

@Composable
fun MapScreenContent(
    state: MapViewState,
    onCameraChanged: (LatLngBounds) -> Unit,
    onSearchButtonClicked: () -> Unit,
) {
    val mapView = rememberMapViewWithLifecycle()
    Box {
        MapView(
            map = mapView,
            itemList = state.cars,
            selectedCar = null,
            onCameraChanged = onCameraChanged,
            currentLocation = state.currentLocation
        )
        TopActionBarButton(
            modifier = Modifier.align(Alignment.TopCenter),
            searchThisAreaButtonClicked = onSearchButtonClicked,
            searchThisAreaButtonState = state.searchAreaButtonText
        )
    }
}

