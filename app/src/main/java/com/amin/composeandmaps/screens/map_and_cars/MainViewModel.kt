package com.amin.composeandmaps.screens.map_and_cars

import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amin.aminChallenge.shared.manager.location.LocationProvider
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.data.usecases.GetItemsForLocationUseCase
import com.amin.composeandmaps.screens.map_and_cars.SearchThisAreaButtonState.*
import com.amin.composeandmaps.shared.manager.location.AminLocationManager
import com.amin.composeandmaps.shared.util.*
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val getItemsForLocationUseCase: GetItemsForLocationUseCase,
    private val aminLocationManager: AminLocationManager,
) : ViewModel(), LocationProvider {

    private var currentCameraBounds: LatLngBounds? = null

    private val cars = MutableStateFlow(emptyList<Car>())
    private val currentLocation = MutableStateFlow(defaultLatLong)
    private val searchThisAreaButtonText = MutableStateFlow(HIDDEN)
    private val operationState = MutableStateFlow(idle() as OperationState)

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(MainViewState())

    val state: StateFlow<MainViewState>
        get() = _state

    init {
        viewModelScope.launch {
            /*
            Combines the latest value from each of the flows, allowing us to generate a
            view state instance which only contains the latest values.
            */
            combine(
                cars,
                currentLocation,
                operationState,
                searchThisAreaButtonText
            ) { cars, currentLocation, operationState, searchThisAreaButtonText ->
                MainViewState(
                    cars = cars,
                    currentLocation = currentLocation,
                    searchAreaButtonText = searchThisAreaButtonText,
                    operationState = operationState
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
    }

    fun searchWithNewBounds(newBounds: LatLngBounds) {
        viewModelScope.launch {
            operationState.value = loading()
            searchThisAreaButtonText.value = LOADING
            val result = getItemsForLocationUseCase(newBounds)
            cars.value = if (result is Result.Success) {
                operationState.value = success()
                searchThisAreaButtonText.value = HIDDEN
                result.value
            } else {
                searchThisAreaButtonText.value = ERROR_TRY_AGAIN
                operationState.value = errorState()
                emptyList()
            }
        }
    }

    /*
    for the purpose of this app we only get one location update and cancel the flow,
    but we could easily listen to location updates using the flow
    */
    @OptIn(InternalCoroutinesApi::class)
    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override fun registerLocationUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                aminLocationManager.startRequestingLocationUpdate().collect {
                    currentLocation.value = it
                    unregisterLocationUpdates()
                    cancel()
                }
            } catch (ex: Exception) {

            }
        }
    }

    fun onCameraBoundsChanged(newBounds: LatLngBounds) {
        if (currentCameraBounds == null) {
            currentCameraBounds = newBounds
            searchThisAreaButtonText.value = VISIBLE
        } else {
            currentCameraBounds?.let {
                if (createLocation(it.center).distanceTo(createLocation(newBounds.center)) > newSearchNeededThreshold) {
                    currentCameraBounds = newBounds
                    searchThisAreaButtonText.value = VISIBLE
                } else {
                    if (searchThisAreaButtonText.value != LOADING)
                        searchThisAreaButtonText.value = HIDDEN
                }
            }
        }
    }

    override fun unregisterLocationUpdates() {
        aminLocationManager.stopRequestingLocationUpdate()
    }

    private fun createLocation(latLng: LatLng) = Location("previous location").apply {
        latitude = latLng.latitude
        longitude = latLng.longitude
    }
}

data class MainViewState(
    val cars: List<Car> = emptyList(),
    val currentLocation: LatLng = defaultLatLong,
    val searchAreaButtonText: SearchThisAreaButtonState = HIDDEN,
    val operationState: OperationState = idle(),
)

enum class SearchThisAreaButtonState {
    HIDDEN, LOADING, VISIBLE, ERROR_TRY_AGAIN
}