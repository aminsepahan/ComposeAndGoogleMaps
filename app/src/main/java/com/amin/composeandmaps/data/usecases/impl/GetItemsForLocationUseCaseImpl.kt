package com.amin.composeandmaps.data.usecases.impl

import com.amin.composeandmaps.data.car.CarRepository
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.data.usecases.GetItemsForLocationUseCase
import com.amin.composeandmaps.shared.util.Result
import com.google.android.libraries.maps.model.LatLngBounds
import javax.inject.Inject

class GetItemsForLocationUseCaseImpl @Inject constructor(private val carRepository: CarRepository) :
    GetItemsForLocationUseCase {
    override suspend fun invoke(latLngBounds: LatLngBounds): Result<List<Car>> {
        return try {
            val cars = carRepository.getAllCars(latLngBounds)
            Result.Success(cars)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}