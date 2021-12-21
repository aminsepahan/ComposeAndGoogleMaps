package com.amin.composeandmaps.data.car.impl

import com.amin.composeandmaps.data.car.CarRepository
import com.amin.composeandmaps.data.car.CarService
import com.amin.composeandmaps.data.models.Car
import javax.inject.Inject


class CarRepositoryImpl @Inject constructor(
    private val service: CarService
) : CarRepository {

    override suspend fun getAllCars(): List<Car> {
        val carsResponse = service.getCars()
        val carsDomain = carsResponse.map {
            it.toDomain()
        }
        return carsDomain
    }

}

private fun CarResponse.toDomain(): Car {
    return Car(
        id = id,
        modelIdentifier = modelIdentifier,
        modelName = modelName,
        name = name,
        make = make,
        group = group,
        color = color,
        series = series,
        fuelType = fuelType,
        fuelLevel = fuelLevel,
        transmission = transmission,
        licensePlate = licensePlate,
        latitude = latitude,
        longitude = longitude,
        innerCleanliness = innerCleanliness,
        carImageUrl = carImageUrl
    )
}






