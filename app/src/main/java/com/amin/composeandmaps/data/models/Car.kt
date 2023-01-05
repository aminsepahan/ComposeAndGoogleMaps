package com.amin.composeandmaps.data.models

import com.amin.composeandmaps.shared.util.defaultLatLong
import com.google.android.libraries.maps.model.LatLng
import kotlin.random.Random

data class Car(
    val id: Int,
    val latLng: LatLng,
    val fleetType: FleetType,
    val heading: Double
) {
    companion object {
        fun mock() = Car(
            id = Random.nextInt(),
            latLng = defaultLatLong,
            fleetType = FleetType.values()[Random.nextInt(FleetType.values().lastIndex)],
            heading = Random.nextDouble()
        )
    }
}


enum class FleetType(val title: String) {
    POOLING("Pooling"), TAXI("Taxi")
}
