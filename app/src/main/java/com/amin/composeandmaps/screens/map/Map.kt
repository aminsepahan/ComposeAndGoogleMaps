package com.amin.composeandmaps.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.viewinterop.AndroidView
import com.amin.composeandmaps.R
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.screens.map_and_cars.defaultLatLong
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch

@Composable
fun MapViewContainer(
    map: MapView,
    itemList: List<Car>?,
    selectedCar: Car?,
    onCameraChanged: (bounds: LatLngBounds) -> Unit,
    currentLocation: LatLng
) {
    val cameraPosition = remember {
        defaultLatLong
    }
    LaunchedEffect(map) {
        val googleMap = map.awaitMap()
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
        googleMap.setOnCameraIdleListener {
            onCameraChanged(googleMap.projection.visibleRegion.latLngBounds)
        }
    }

    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            googleMap.uiSettings.isZoomControlsEnabled = true
            var zoom = 9f
            var destination = currentLocation
            if (selectedCar != null) {
                zoom = 15f
                destination = selectedCar.latLong
            }
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, zoom))
            itemList?.forEach { car ->
                val markerOptionsDestination = MarkerOptions()
                    .title("${car.name} ${car.modelName}")
                    .position(car.latLong)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
                googleMap.addMarker(markerOptionsDestination)
            }
        }
    }
}