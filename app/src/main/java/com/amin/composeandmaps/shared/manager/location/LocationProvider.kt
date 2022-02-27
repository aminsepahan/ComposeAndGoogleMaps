package com.amin.aminChallenge.shared.manager.location

import androidx.annotation.RequiresPermission

interface LocationProvider {

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    fun registerLocationUpdates()

    fun unregisterLocationUpdates()
}
