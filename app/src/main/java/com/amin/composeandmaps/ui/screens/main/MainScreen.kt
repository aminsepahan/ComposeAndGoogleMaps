package com.amin.composeandmaps.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.amin.composeandmaps.ui.screens.welcome.MainViewModel
import com.amin.composeandmaps.ui.theme.screenBack
import com.amin.composeandmaps.ui.utils.rememberMapViewWithLifecycle
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    MainScreenContent()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent() {
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
                    LazyColumn {
                        item {
                            for (i in 1..15){
                                Text(text = "item $i")
                            }
                        }
                    }
                }
            },
        ) {
            Map()
            Button(onClick = {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.isVisible) {
                        bottomSheetScaffoldState.hide()
                    } else {
                        bottomSheetScaffoldState.show()
                    }
                }
            }) {
                Text(text = "Expand/Collapse Bottom Sheet")
            }
        }
    }
}

@Composable
fun Map(){
    val mapView = rememberMapViewWithLifecycle()
    AndroidView({ mapView }) { createdMapView ->
        CoroutineScope(Dispatchers.Main).launch {
            val map = createdMapView.awaitMap()
            map.uiSettings.isZoomControlsEnabled = true
            val destination = LatLng(-32.491, 147.309)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 6f))
        }
    }
}

@Preview
@Composable
fun ContentPreview() {
    MainScreenContent()
}