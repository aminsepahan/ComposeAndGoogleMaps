package com.amin.composeandmaps.data.car.impl

import com.amin.composeandmaps.data.car.CarRepository
import com.amin.composeandmaps.data.car.CarService
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.data.models.FleetType
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import retrofit2.http.Query
import javax.inject.Inject


class CarRepositoryImpl @Inject constructor(
    private val service: CarService
) : CarRepository {

    override suspend fun getAllCars(latLngBounds: LatLngBounds): List<Car> {

        val p1Lat = latLngBounds.southwest.latitude
        val p1Lon = latLngBounds.southwest.longitude
        val p2Lat = latLngBounds.northeast.latitude
        val p2Lon = latLngBounds.northeast.longitude
        val carsResponse = service.getCars(
            p1Lat = p1Lat,
            p1Lon = p1Lon,
            p2Lat = p2Lat,
            p2Lon = p2Lon
        )
        val carsDomain = carsResponse.poiList.map {
            it.toDomain()
        }
        return carsDomain
    }

    private fun CarRemoteModel.toDomain(): Car {
        return Car(
            id = id,
            latLng = LatLng(coordinate.latitude, coordinate.longitude),
            fleetType = fleetType.toDomain(),
            heading = heading
        )
    }

    fun FleetTypeRemote.toDomain() = when(this) {
        FleetTypeRemote.POOLING -> FleetType.POOLING
        FleetTypeRemote.TAXI -> FleetType.TAXI
    }
}






