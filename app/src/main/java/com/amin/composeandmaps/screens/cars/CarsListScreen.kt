package com.amin.composeandmaps.screens.cars

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.screens.map.MapViewModel
import com.amin.composeandmaps.screens.map_and_cars.SearchThisAreaButtonState
import com.amin.composeandmaps.screens.map_and_cars.TopActionBarButton
import com.amin.composeandmaps.shared.util.defaultPadding
import com.google.android.libraries.maps.model.LatLngBounds

@Composable
fun CarListScreen(
    viewModel: MapViewModel,
) {
    val state = viewModel.state.collectAsState()
    CarListContent(cars = state.value.cars) { newBounds ->
        viewModel.searchWithNewBounds(newBounds)
    }
}

@Composable
fun CarListContent(cars: List<Car>, onSearchClicked: (LatLngBounds) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(onSearchClicked)
        CarList(cars = cars, onItemClicked = {})
    }
}

@Composable
fun SearchBar(onSearchClicked: (LatLngBounds) -> Unit) {
    var northwest by rememberSaveable { mutableStateOf(Pair("", "")) }
    var southeast by rememberSaveable { mutableStateOf(Pair("", "")) }
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(defaultPadding.dp),
            text = "Enter the coordinates"
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth(0.45f)) {
                CoordinateTextField(
                    title = "northwest lat",
                    text = northwest.first,
                    onTextChange = {
                        northwest = northwest.copy(first = it)
                    },
                    onKeyboardDoneAction = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
                CoordinateTextField(
                    title = "northwest long",
                    text = northwest.second,
                    onTextChange = {
                        northwest = northwest.copy(second = it)
                    },
                    onKeyboardDoneAction = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
            }
            Divider(modifier = Modifier.fillMaxWidth(0.1f))
            Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                CoordinateTextField(
                    title = "southeast lat",
                    text = southeast.first,
                    onTextChange = {
                        southeast = southeast.copy(first = it)
                    },
                    onKeyboardDoneAction = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
                CoordinateTextField(
                    title = "southeast long",
                    text = southeast.second,
                    onTextChange = {
                        southeast = southeast.copy(second = it)
                    },
                    onKeyboardDoneAction = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
            }

        }
        TopActionBarButton(
            modifier = Modifier.align(CenterHorizontally),
            searchThisAreaButtonClicked = {

            },
            searchThisAreaButtonState = SearchThisAreaButtonState.VISIBLE
        )
    }
}

@Composable
fun CoordinateTextField(
    title: String,
    text: String,
    onTextChange: (String) -> Unit,
    onKeyboardDoneAction: () -> Unit
) {
    OutlinedTextField(
        singleLine = true,
        value = text,
        onValueChange = onTextChange,
        label = {
            Text(
                text = title
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onKeyboardDoneAction()
        }),
        modifier = Modifier
            .padding(horizontal = defaultPadding.dp)
            .fillMaxWidth()
            .padding(bottom = 8.dp),
    )
}


@Preview
@Composable
fun CarListPreview() {
    val fakeCarList = mutableListOf(Car.mock())
    for (i in 1..5) {
        fakeCarList.add(Car.mock())
    }
    CarListContent(fakeCarList) {

    }
}