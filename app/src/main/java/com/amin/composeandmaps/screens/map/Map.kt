package com.amin.composeandmaps.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import com.amin.composeandmaps.R
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.data.models.FleetType
import com.amin.composeandmaps.shared.util.defaultLatLong
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap

@Composable
fun MapView(
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 9f))
        googleMap.setOnCameraIdleListener {
            onCameraChanged(googleMap.projection.visibleRegion.latLngBounds)
        }
    }
    LaunchedEffect(currentLocation) {
        val googleMap = map.awaitMap()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 11f))
    }
    LaunchedEffect(key1 = selectedCar) {
        if (selectedCar != null) {
            val googleMap = map.awaitMap()
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedCar.latLng, 14f))
        }
    }
    LaunchedEffect(key1 = itemList) {
        val googleMap = map.awaitMap()
        googleMap.clear()
        itemList?.forEach { car ->
            val markerOptionsDestination = MarkerOptions()
                .title("${car.fleetType.title} ${car.heading}")
                .position(car.latLng)
                .icon(
                    BitmapDescriptorFactory.fromResource(
                        when (car.fleetType) {
                            FleetType.POOLING -> R.drawable.ic_car_pooling
                            FleetType.TAXI -> R.drawable.ic_car_taxi
                        }
                    )
                )
            googleMap.addMarker(markerOptionsDestination)
        }
    }
    AndroidView({ map })
}