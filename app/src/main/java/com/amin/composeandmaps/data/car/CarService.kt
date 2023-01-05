package com.amin.composeandmaps.data.car

import com.amin.composeandmaps.data.car.impl.CarResponse
import com.amin.composeandmaps.shared.util.BASE_URL
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CarService {
    @GET("")
    suspend fun getCars(
        @Url url: String = BASE_URL,
        @Query("p1Lat") p1Lat: Double,
        @Query("p1Lon") p1Lon: Double,
        @Query("p2Lat") p2Lat: Double,
        @Query("p2Lon") p2Lon: Double,
    ): CarResponse
}
