package com.amin.composeandmaps.data.models

data class Car(
    val carImageUrl: String,
    val color: String,
    val fuelLevel: Double,
    val fuelType: String,
    val group: String,
    val id: String,
    val innerCleanliness: String,
    val latitude: Double,
    val licensePlate: String,
    val longitude: Double,
    val make: String,
    val modelIdentifier: String,
    val modelName: String,
    val name: String,
    val series: String,
    val transmission: String
) {
    companion object {
        fun mock() = Car(
            id = "WMWSW31030T222518",
            modelIdentifier = "mini",
            modelName = "MINI",
            name = "Vanessa",
            make = "BMW",
            group = "MINI",
            color = "midnight_black",
            series = "MINI",
            fuelType = "D",
            fuelLevel = 0.7,
            transmission = "M",
            licensePlate = "M-VO0259",
            latitude = 48.134557,
            longitude = 11.576921,
            innerCleanliness = "REGULAR",
            carImageUrl = "https://cdn.sixt.io/codingtask/images/mini.png"
        )
    }
}
