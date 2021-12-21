package com.amin.composeandmaps.data.car

import com.amin.composeandmaps.data.models.Car


interface CarRepository {

    suspend fun getAllCars(): List<Car>
}

