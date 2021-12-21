package com.amin.composeandmaps.data.car

import com.amin.composeandmaps.data.car.impl.CarResponse
import retrofit2.http.GET

interface CarService {
    @GET("cars")
    suspend fun getCars(
    ): List<CarResponse>
}
