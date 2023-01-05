package com.amin.composeandmaps.data.car.impl

import com.amin.composeandmaps.data.models.FleetType

data class CarResponse(
    val poiList: List<CarRemoteModel>
)
data class CarRemoteModel(
    val id: Int,
    val coordinate: Coordinate,
    val fleetType: FleetTypeRemote,
    val heading: Double
) {
    data class Coordinate(
        val latitude: Double,
        val longitude: Double
    )

}

enum class FleetTypeRemote {
    POOLING, TAXI
}
