package com.amin.composeandmaps.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.amin.composeandmaps.ui.theme.screenBack
import com.amin.composeandmaps.ui.theme.white
import com.amin.composeandmaps.ui.utils.rememberMapViewWithLifecycle
import com.amin.composeandmaps.utils.UIState
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val carsState by viewModel.carsState.observeAsState(UIState.Idle())
    MainScreenContent(carsState) {
        viewModel.loadItems()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent(state: UIState<List<Car>>, tryAgain: () -> Unit) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    var selectedCar: Car? by remember {
        mutableStateOf(null)
    }
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBack),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomSheetContent(state) {
                selectedCar = it
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }
    ) {

        MainScreen(state, selectedCar, bottomSheetScaffoldState, scope, tryAgain)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainScreen(
    state: UIState<List<Car>>,
    selectedCar: Car?,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    tryAgain: () -> Unit
) {
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(mapView, state, selectedCar)
}

@Composable
fun BottomSheetContent(state: UIState<List<Car>>, onItemClicked: (item: Car) -> Job) = Box(
    Modifier
        .fillMaxWidth()
        .height(400.dp)
) {
    if (state.isSuccess()) {
        LazyColumn {
            item {
                state.data?.forEach { item ->
                    CarCardItem(item) {
                        onItemClicked(item)
                    }
                }
            }
        }
    } else {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Still loading, try again in a few seconds ..."
        )
    }
}


@Composable
private fun MapViewContainer(
    map: MapView,
    state: UIState<List<Car>>,
    selectedCar: Car?
) {
    val cameraPosition = remember {
        defaultLatLong
    }

    LaunchedEffect(map) {
        val googleMap = map.awaitMap()
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
    }

    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            googleMap.uiSettings.isZoomControlsEnabled = true
            var zoom = 9f
            var destination = defaultLatLong
            when {
                selectedCar != null -> {
                    zoom = 15f
                    destination = selectedCar.latLong
                }
                state.isSuccess() && !state.data.isNullOrEmpty() -> {
                    zoom = 12f
                    val car = state.data!![0]
                    destination = car.latLong
                }
            }
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, zoom))
            if (state.isSuccess()) {
                state.data?.forEach { car ->
                    val markerOptionsDestination = MarkerOptions()
                        .title("${car.name} ${car.modelName}")
                        .position(car.latLong)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
                    googleMap.addMarker(markerOptionsDestination)
                }
            }
        }
    }
}

@Composable
fun CarCardItem(car: Car, onCarCardClicked: () -> Unit) {
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
            ImageItem(imageUrl = car.carImageUrl)
            Text(text = "${car.name}, ${car.modelName}")
        }
    }
}

@Composable
fun ImageItem(imageUrl: String) {
    GlideImage(
        imageModel = imageUrl,
        contentScale = ContentScale.Inside,
        placeHolder = ImageVector.vectorResource(R.drawable.ic_simple_car_side),
        error = ImageVector.vectorResource(R.drawable.ic_simple_car_side),
        modifier = Modifier
            .size(60.dp)
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
    MainScreenContent(UIState.Idle(), {})
}

@Preview
@Composable
fun ContentSuccessPreview() {
    MainScreenContent(UIState.Success(listOf(Car.mock())), {})
}

private const val InitialZoom = 5f
const val MinZoom = 2f
const val MaxZoom = 20f
val defaultLatLong = LatLng(51.362331, 9.674301)