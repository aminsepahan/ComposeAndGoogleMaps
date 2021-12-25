package com.amin.composeandmaps.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.amin.composeandmaps.R
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.ui.theme.AppCard
import com.amin.composeandmaps.ui.theme.AppIcon
import com.amin.composeandmaps.ui.theme.screenBack
import com.amin.composeandmaps.ui.theme.white
import com.amin.composeandmaps.ui.utils.rememberMapViewWithLifecycle
import com.amin.composeandmaps.ui.utils.setZoom
import com.amin.composeandmaps.utils.UIState
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
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
                        .height(280.dp)
                ) {
                    if (state.isSuccess()) {
                        LazyColumn {
                            item {
                                state.data?.forEach { car ->
                                    CarCardItem(car)
                                }
                            }
                        }
                    } else {
                        Text("Still loading, try again in a few seconds ...")
                    }
                }
            },
        ) {
            val mapView = rememberMapViewWithLifecycle()
            MapViewContainer(mapView, state)
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
private fun MapViewContainer(
    map: MapView,
    state: UIState<List<Car>>
) {
    val cameraPosition = remember {
        defaultLatLong
    }

    LaunchedEffect(map) {
        val googleMap = map.awaitMap()
        googleMap.addMarker { position(cameraPosition) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
    }

    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            googleMap.uiSettings.isZoomControlsEnabled = true
            val destination = if (state.isSuccess() && !state.data.isNullOrEmpty()) {
                val car = state.data!![0]
                car.latLong
            } else {
                defaultLatLong
            }
            if (state.isSuccess()) {
                state.data?.forEach { car ->
                    val markerOptionsDestination = MarkerOptions()
                        .title("${car.name} ${car.modelName}")
                        .position(car.latLong)
                    googleMap.addMarker(markerOptionsDestination)
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 10f))
            }
        }
    }
}

@Composable
fun CarCardItem(car: Car) {
    AppCard {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageItem(imageUrl = car.carImageUrl)
            Text(text = car.name)
        }
    }
}

@Composable
fun ImageItem(imageUrl: String) {
    GlideImage(
        imageModel = imageUrl,
        contentScale = ContentScale.Inside,
        circularReveal = CircularReveal(duration = 250),
        placeHolder = ImageVector.vectorResource(R.drawable.ic_simple_car_side),
        error = ImageVector.vectorResource(R.drawable.ic_simple_car_side),
        modifier = Modifier
            .padding(5.dp)
            .size(70.dp)
            .shadow(0.dp, shape = RoundedCornerShape(6.dp), clip = true)
    )
}

@Preview
@Composable
fun ImageItemPreview() {
    ImageItem(imageUrl = "https://cdn.sixt.io/codingtask/images/mini.png")
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

private const val InitialZoom = 5f
const val MinZoom = 2f
const val MaxZoom = 20f
val defaultLatLong = LatLng(51.362331, 9.674301)