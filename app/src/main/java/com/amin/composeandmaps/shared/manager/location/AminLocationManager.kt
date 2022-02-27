package com.amin.composeandmaps.shared.manager.location

import android.content.Context
import androidx.annotation.RequiresPermission
import com.amin.composeandmaps.shared.exceptions.GpsOrNetworkLocationNotEnabledException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.libraries.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

/* Its named this way to avoid confusion with android location manager */
interface AminLocationManager {

    val lastKnownLocation: LatLng?

    /* We are using flow here because we might need to get continuous location updates */
    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    @Throws(
        Exception::class,
        ResolvableApiException::class,
        GpsOrNetworkLocationNotEnabledException::class
    )
    suspend fun startRequestingLocationUpdate(): Flow<LatLng>

    fun stopRequestingLocationUpdate()

    suspend fun isLocationProviderEnabled(context: Context) : Boolean
}
