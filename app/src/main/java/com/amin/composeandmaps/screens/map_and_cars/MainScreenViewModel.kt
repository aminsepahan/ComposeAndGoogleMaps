package com.amin.composeandmaps.screens.map_and_cars

import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amin.aminChallenge.shared.manager.location.LocationProvider
import com.amin.composeandmaps.data.car.CarRepository
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.shared.manager.location.AminLocationManager
import com.amin.composeandmaps.shared.util.UIState
import com.amin.composeandmaps.shared.util.loading
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val carRepository: CarRepository,
    private val aminLocationManager: AminLocationManager,
) : ViewModel(), LocationProvider {

    private val _carsState by lazy { MutableLiveData<UIState<List<Car>>>() }
    val carsState: LiveData<UIState<List<Car>>> = _carsState

    private val _currentLocation = MutableSharedFlow<LatLng>(0)
    val currentLocation: SharedFlow<LatLng> = _currentLocation.asSharedFlow()

    private var currentCameraBounds: LatLngBounds? = null

    private val _searchThisAreaButtonText by lazy { MutableLiveData<SearchThisAreaButtonState>() }
    val searchThisAreaButtonText: LiveData<SearchThisAreaButtonState> = _searchThisAreaButtonText

    fun loadItems(newBounds: LatLngBounds) {
        viewModelScope.launch {
            _carsState.postValue(loading())
            val cars = carRepository.getAllCars(newBounds)
            _carsState.postValue(UIState.Success(cars))
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
                    _currentLocation.emit(it)
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
            _searchThisAreaButtonText.postValue(SearchThisAreaButtonState.VISIBLE)
        } else {
            currentCameraBounds?.let {
                val previousLocation = Location("previous location")
                previousLocation.latitude = it.center.latitude
                previousLocation.longitude = it.center.longitude
                val newLocation = Location("previous location")
                previousLocation.latitude = newBounds.center.latitude
                previousLocation.longitude = newBounds.center.longitude
                if (previousLocation.distanceTo(newLocation) > 500) {
                    currentCameraBounds = newBounds
                    _searchThisAreaButtonText.postValue(SearchThisAreaButtonState.VISIBLE)
                } else {
                    if (_searchThisAreaButtonText.value != SearchThisAreaButtonState.LOADING)
                        _searchThisAreaButtonText.postValue(SearchThisAreaButtonState.HIDDEN)
                }
            }
        }
    }

    override fun unregisterLocationUpdates() {
        aminLocationManager.stopRequestingLocationUpdate()
    }

    fun searchWithNewBounds(it: LatLngBounds) {
        loadItems(it)
    }
}

enum class SearchThisAreaButtonState {
    HIDDEN, LOADING, VISIBLE, ERROR_TRY_AGAIN
}