package com.amin.composeandmaps.screens.map_and_cars

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.R
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.screens.cars.CarList
import com.amin.composeandmaps.shared.helper.toImageResourceId
import com.amin.composeandmaps.shared.theme.screenBack
import com.amin.composeandmaps.shared.util.OperationState
import com.amin.composeandmaps.shared.util.defaultPadding
import com.amin.composeandmaps.shared.util.sheetPeekHeight
import com.google.android.libraries.maps.model.LatLngBounds
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun MainScreen(
    viewModel: CarsViewModel,
) {
    val state by viewModel.state.collectAsState()
    var currentCameraBounds: LatLngBounds? by remember {
        mutableStateOf(null)
    }
    MainScreenContent(
        state = state,
        onLocationClicked = {
            viewModel.registerLocationUpdates()
        },
        searchWithNewBounds = {
            currentCameraBounds?.let {
                viewModel.searchWithNewBounds(it)
            }
        },
        currentCameraBoundsChanged = { newBounds ->
            currentCameraBounds = newBounds
            viewModel.onCameraBoundsChanged(newBounds)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent(
    state: MainViewState,
    searchWithNewBounds: () -> Unit,
    onLocationClicked: () -> Unit,
    currentCameraBoundsChanged: (LatLngBounds) -> Unit,
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    var selectedCar: Car? by remember {
        mutableStateOf(null)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = state.operationState) {
        if(state.operationState is OperationState.Loading) {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }
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
            scope = scope,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            onCameraChanged = {
                selectedCar = null
                currentCameraBoundsChanged(it)
            },
            searchThisAreaButtonClicked = searchWithNewBounds,
            onLocationClicked = onLocationClicked,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    state: MainViewState,
    onItemClicked: (item: Car) -> Unit,
    bottomSheetState: BottomSheetState
) = Box(
    Modifier
        .fillMaxWidth()
        .height(400.dp)
) {
    when (state.operationState) {
        is OperationState.Success -> {
            if (bottomSheetState.isExpanded) {
                CarList(state.cars, onItemClicked)
            } else {
                BottomSheetSuccessAndCollapsedContent(state.cars)
            }
        }
        is OperationState.Loading -> {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Still loading, try again in a few seconds ..."
            )
        }
        is OperationState.Error -> {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Something went wrong try again!"
            )
        }
        else -> {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Press the search button to get the list of cars for this area"
            )
        }
    }
}

@Composable
fun BottomSheetSuccessAndCollapsedContent(cars: List<Car>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(sheetPeekHeight.dp)
            .padding(start = defaultPadding.dp, top = defaultPadding.dp, bottom = defaultPadding.dp)
    ) {
        cars.take(5).forEach {
            SmallItemThumbnail(it)
        }
    }
}

@Composable
fun SmallItemThumbnail(item: Car) {
    Image(
        painter = painterResource(item.fleetType.toImageResourceId()),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(end = defaultPadding.dp)
            .size((sheetPeekHeight - defaultPadding).dp)
            .shadow(0.dp, shape = RoundedCornerShape(6.dp), clip = true),
        contentDescription = "car image"
    )
}


@Composable
fun ImageItem(imageResourceId: Int) {
    Image(
        painter = painterResource(imageResourceId),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(60.dp)
            .shadow(0.dp, shape = RoundedCornerShape(6.dp), clip = true),
        contentDescription = "Car image"
    )
}

@Preview
@Composable
fun ImageItemPreview() {
    ImageItem(imageResourceId = R.drawable.pooling)
}