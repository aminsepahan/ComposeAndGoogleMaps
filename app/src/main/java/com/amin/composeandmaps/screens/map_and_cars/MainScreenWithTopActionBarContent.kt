package com.amin.composeandmaps.screens.map_and_cars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.R
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.screens.demo.DemoSpacer
import com.amin.composeandmaps.screens.map.MapView
import com.amin.composeandmaps.screens.map_and_cars.SearchThisAreaButtonState.*
import com.amin.composeandmaps.shared.theme.Purple500
import com.amin.composeandmaps.shared.util.rememberMapViewWithLifecycle
import com.google.android.libraries.maps.model.LatLngBounds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenWithTopActionBarContent(
    state: MainViewState,
    selectedCar: Car?,
    scope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onCameraChanged: (bounds: LatLngBounds) -> Unit,
    onLocationClicked: () -> Unit,
    searchThisAreaButtonClicked: () -> Unit,
) {
    Box {
        val mapView = rememberMapViewWithLifecycle()
        MapView(
            map = mapView,
            itemList = state.cars,
            selectedCar = selectedCar,
            onCameraChanged = onCameraChanged,
            currentLocation = state.currentLocation
        )
        MainScreenTopActionBar(
            scope = scope,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            onLocationClicked = onLocationClicked,
            searchThisAreaButtonState = state.searchAreaButtonText,
            searchThisAreaButtonClicked = searchThisAreaButtonClicked
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainScreenTopActionBar(
    scope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onLocationClicked: () -> Unit,
    searchThisAreaButtonState: SearchThisAreaButtonState,
    searchThisAreaButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .shadow(shape = RoundedCornerShape(5.dp), elevation = 3.dp)
                .background(color = Color.White)
        ) {
            TopActionBarButton(
                searchThisAreaButtonClicked = searchThisAreaButtonClicked,
                searchThisAreaButtonState = searchThisAreaButtonState
            )
            MapIconButton(imageVector = Icons.Filled.List, onClicked = {
                toggleBottomSheet(scope, bottomSheetScaffoldState)
            })
            MapIconButton(imageVector = Icons.Filled.LocationOn, onClicked = onLocationClicked)
        }

    }
}

@Composable
fun TopActionBarButton(
    modifier: Modifier = Modifier,
    searchThisAreaButtonClicked: () -> Unit,
    searchThisAreaButtonState: SearchThisAreaButtonState
) {
    Button(
        modifier = modifier
            .padding(start = 8.dp, end = 4.dp)
            .fillMaxWidth(0.70f),
        onClick = {
            searchThisAreaButtonClicked()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = when (searchThisAreaButtonState) {
                VISIBLE -> Purple500
                ERROR_TRY_AGAIN -> Color.Red
                else -> Color.White
            }
        )
    ) {
        val buttonText = when (searchThisAreaButtonState) {
            HIDDEN -> "Move Further"
            LOADING -> stringResource(id = R.string.searching_for_items_here)
            VISIBLE -> stringResource(R.string.search_this_area)
            ERROR_TRY_AGAIN -> stringResource(R.string.something_went_wrong_try_again)
        }
        Text(
            text = buttonText, color = when (searchThisAreaButtonState) {
                LOADING, HIDDEN -> Color.Gray
                else -> Color.White
            }
        )
    }
}

enum class SearchThisAreaButtonState {
    HIDDEN, LOADING, VISIBLE, ERROR_TRY_AGAIN
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun TopActionBarHiddenPreview() {
    Column {
        MainScreenTopActionBar(
            scope = rememberCoroutineScope(),
            bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
            onLocationClicked = { },
            searchThisAreaButtonState = LOADING,
            searchThisAreaButtonClicked = {}
        )
        DemoSpacer()
        MainScreenTopActionBar(
            scope = rememberCoroutineScope(),
            bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
            onLocationClicked = { },
            searchThisAreaButtonState = VISIBLE,
            searchThisAreaButtonClicked = {}
        )
        DemoSpacer()
        MainScreenTopActionBar(
            scope = rememberCoroutineScope(),
            bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
            onLocationClicked = { },
            searchThisAreaButtonState = ERROR_TRY_AGAIN,
            searchThisAreaButtonClicked = {}
        )
        DemoSpacer()
        MainScreenTopActionBar(
            scope = rememberCoroutineScope(),
            bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
            onLocationClicked = { },
            searchThisAreaButtonState = HIDDEN,
            searchThisAreaButtonClicked = {}
        )
        DemoSpacer()
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun toggleBottomSheet(
    scope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    scope.launch {
        if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        } else {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }
}

@Composable
fun MapIconButton(
    imageVector: ImageVector,
    onClicked: () -> Unit,
    contentDescription: String? = null,
) {
    IconButton(
        onClick = onClicked
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = Purple500,
            modifier = Modifier
                .padding(end = 8.dp, start = 8.dp),
        )
    }
}