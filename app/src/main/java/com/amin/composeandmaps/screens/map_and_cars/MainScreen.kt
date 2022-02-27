package com.amin.composeandmaps.screens.map_and_cars

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.R
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.screens.cars.CarList
import com.amin.composeandmaps.screens.map_and_cars.SearchThisAreaButtonState.*
import com.amin.composeandmaps.shared.theme.screenBack
import com.amin.composeandmaps.shared.util.UIState
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun MainScreen(
    viewModel: CarsViewModel,
) {
    val carsState by viewModel.carsState.observeAsState(UIState.Idle())
    val currentLocation by viewModel.currentLocation.collectAsState(initial = defaultLatLong)
    var currentCameraBounds: LatLngBounds? by remember {
        mutableStateOf(null)
    }
    val searchThisAreaButtonState by viewModel.searchThisAreaButtonText.observeAsState(LOADING)
    MainScreenContent(
        state = carsState,
        searchWithNewBounds = {
            currentCameraBounds?.let {
                viewModel.loadItems(it)
            }
        },
        currentLocation = currentLocation,
        onLocationClicked = {
            viewModel.registerLocationUpdates()
        },
        searchThisAreaButtonState = searchThisAreaButtonState,
        currentCameraBoundsChanged = { newBounds ->
            viewModel.onCameraBoundsChanged(newBounds)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent(
    state: UIState<List<Car>>,
    searchWithNewBounds: () -> Unit,
    currentLocation: LatLng,
    onLocationClicked: () -> Unit,
    currentCameraBoundsChanged: (LatLngBounds) -> Unit,
    searchThisAreaButtonState: SearchThisAreaButtonState
) {
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
            BottomSheetContent(state = state, onItemClicked = {
                selectedCar = it
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }, bottomSheetState = bottomSheetScaffoldState.bottomSheetState)
        },
        sheetPeekHeight = sheetPeekHeight.dp
    ) {
        MainScreenWithTopActionBarContent(
            state = state,
            selectedCar = selectedCar,
            currentLocation = currentLocation,
            scope = scope,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            onCameraChanged = currentCameraBoundsChanged,
            searchThisAreaButtonClicked = searchWithNewBounds,
            onLocationClicked = onLocationClicked,
            searchThisAreaButtonState = searchThisAreaButtonState
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    state: UIState<List<Car>>,
    onItemClicked: (item: Car) -> Unit,
    bottomSheetState: BottomSheetState
) = Box(
    Modifier
        .fillMaxWidth()
        .height(400.dp)
) {
    if (state.isSuccess()) {
        if (bottomSheetState.isExpanded) {
            CarList(state, onItemClicked)
        } else {
            BottomSheetSuccessAndCollapsedContent(state)
        }
    } else {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Still loading, try again in a few seconds ..."
        )
    }
}

@Composable
fun BottomSheetSuccessAndCollapsedContent(state: UIState<List<Car>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(sheetPeekHeight.dp)
            .padding(start = defaultPadding.dp, top = defaultPadding.dp, bottom = defaultPadding.dp)
    ) {
        state.data?.take(5)?.forEach {
            SmallItemThumbnail(it)
        }
    }
}

@Composable
fun SmallItemThumbnail(item: Car) {
    GlideImage(
        imageModel = item.carImageUrl,
        contentScale = ContentScale.Inside,
        placeHolder = ImageVector.vectorResource(R.drawable.ic_simple_car_side),
        error = ImageVector.vectorResource(R.drawable.ic_simple_car_side),
        modifier = Modifier
            .padding(end = defaultPadding.dp)
            .size((sheetPeekHeight - defaultPadding).dp)
            .shadow(0.dp, shape = RoundedCornerShape(6.dp), clip = true)
    )
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

private const val InitialZoom = 5f
const val MinZoom = 2f
const val MaxZoom = 20f
const val sheetPeekHeight = 80
const val defaultPadding = 16
val defaultLatLong = LatLng(51.362331, 9.674301)