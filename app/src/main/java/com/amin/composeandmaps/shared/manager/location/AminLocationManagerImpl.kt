package com.amin.composeandmaps.shared.manager.location

import android.content.Context
import androidx.annotation.RequiresPermission
import com.amin.composeandmaps.App.Companion.appContext
import com.amin.composeandmaps.shared.exceptions.GpsOrNetworkLocationNotEnabledException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.libraries.maps.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AminLocationManagerImpl : AminLocationManager {

    private val locationRequest = LocationRequest.create().apply {
        interval = 40000
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)
    private var locationCallback: LocationCallback? = null

    override var lastKnownLocation: LatLng? = null

    private var currentLocationFlow: Flow<LatLng>? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    @Synchronized
    override suspend fun startRequestingLocationUpdate(): Flow<LatLng> {
        currentLocationFlow?.let {
            return it
        }
        val isLocationProviderEnabled = isLocationProviderEnabled(appContext)
        if (!isLocationProviderEnabled) {
            throw GpsOrNetworkLocationNotEnabledException()
        }
        currentLocationFlow = callbackFlow {
            locationCallback = object : LocationCallback() {
                override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                }

                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.locations[0]
                    val locationLatLng = LatLng(location.latitude, location.longitude)
                    lastKnownLocation = locationLatLng
                    trySend(locationLatLng)
                }
            }
            appContext.mainLooper?.let { looper ->
                locationCallback?.let { locationCallback ->
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        looper
                    )
                }
            }

            awaitClose {
                stopRequestingLocationUpdate()
            }
        }
        return currentLocationFlow!!
    }

    override fun stopRequestingLocationUpdate() {
        Timber.tag("Location").i("stopRequestingLocationUpdate()")
        locationCallback?.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
        locationCallback = null
        currentLocationFlow = null
        lastKnownLocation = null
    }


    override suspend fun isLocationProviderEnabled(context: Context): Boolean {
        val builder = LocationSettingsRequest.Builder()
        val settingsClient: SettingsClient = LocationServices.getSettingsClient(context)
        val checkLocationSettingsTask: Task<LocationSettingsResponse> =
            settingsClient.checkLocationSettings(builder.build())
        return suspendCoroutine { continuation ->
            checkLocationSettingsTask.addOnSuccessListener { response ->
                Timber.tag("Location").i("checkLocationSettingsTask OnSuccess")
                response.locationSettingsStates?.let {
                    val isGpsOrNetworkEnabled = it.isLocationPresent && it.isLocationUsable
                    continuation.resume(isGpsOrNetworkEnabled)
                }
            }
            checkLocationSettingsTask.addOnFailureListener { exception ->
                Timber.tag("Location").i("checkLocationSettingsTask OnError")
                continuation.resumeWithException(exception = exception)
            }
        }
    }
}