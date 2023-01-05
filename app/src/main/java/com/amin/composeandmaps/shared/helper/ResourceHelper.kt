package com.amin.composeandmaps.shared.helper

import com.amin.composeandmaps.R
import com.amin.composeandmaps.data.models.FleetType

fun FleetType.toImageResourceId() = when (this) {
    FleetType.POOLING -> R.drawable.pooling
    FleetType.TAXI -> R.drawable.taxi
}