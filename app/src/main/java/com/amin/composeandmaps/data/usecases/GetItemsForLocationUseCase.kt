package com.amin.composeandmaps.data.usecases

import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.shared.util.Result
import com.google.android.libraries.maps.model.LatLngBounds

interface GetItemsForLocationUseCase {
    suspend operator fun invoke(latLngBounds: LatLngBounds): Result<List<Car>>
}