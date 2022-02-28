package com.amin.composeandmaps.data.car

import com.amin.composeandmaps.data.models.Car
import com.google.android.libraries.maps.model.LatLngBounds


interface CarRepository {

    suspend fun getAllCars(latLngBounds: LatLngBounds): List<Car>
}

