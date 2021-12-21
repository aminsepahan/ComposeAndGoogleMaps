package com.amin.composeandmaps.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.ui.theme.AppIcon
import com.amin.composeandmaps.ui.theme.screenBack
import com.amin.composeandmaps.ui.theme.white
import com.amin.composeandmaps.ui.utils.rememberMapViewWithLifecycle
import com.amin.composeandmaps.utils.UIState
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val carsState by viewModel.carsState.observeAsState(UIState.Idle())
    MainScreenContent(carsState)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent(state: UIState<List<Car>>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(screenBack),
    ) {
        val bottomSheetScaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()
        ModalBottomSheetLayout(
            sheetState = bottomSheetScaffoldState,
            sheetContent = {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    if (state.isSuccess()) {
                        LazyColumn {
                            item {
                                state.data?.forEach {
                                    Text(text = "item ${it.name} ${it.modelName}")
                                }
                            }
                        }
                    } else {
                        Text("Still loading, try again in a few seconds ...")
                    }
                }
            },
        ) {
            Map(state)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(onClick = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.isVisible) {
                            bottomSheetScaffoldState.hide()
                        } else {
                            bottomSheetScaffoldState.show()
                        }
                    }
                }) {
                    AppIcon(
                        imageVector = Icons.Filled.List,
                        tint = white,
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(text = "Show cars list")
                }
            }
        }
    }
}

@Composable
fun Map(state: UIState<List<Car>>) {
    val mapView = rememberMapViewWithLifecycle()
    AndroidView({ mapView }) { createdMapView ->
        CoroutineScope(Dispatchers.Main).launch {
            val map = createdMapView.awaitMap()
            map.uiSettings.isZoomControlsEnabled = true
            val destination = if (state.isSuccess() && !state.data.isNullOrEmpty()) {
                val car = state.data!![0]
                LatLng(car.latitude, car.longitude)
            } else {
                LatLng(-32.491, 147.309)
            }
            if (state.isSuccess()) {
                state.data?.forEach { car ->
                    val markerOptionsDestination = MarkerOptions()
                        .title("${car.name} ${car.modelName}")
                        .position(LatLng(car.latitude, car.longitude))
                    map.addMarker(markerOptionsDestination)
                }
            }
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 10f))
        }
    }
}

@Preview
@Composable
fun ContentIdlePreview() {
    MainScreenContent(UIState.Idle())
}

@Preview
@Composable
fun ContentSuccessPreview() {
    MainScreenContent(UIState.Success(listOf(Car.mock())))
}