package com.amin.composeandmaps.screens.cars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.data.usecases.GetItemsForLocationUseCase
import com.amin.composeandmaps.shared.manager.location.AminLocationManager
import com.amin.composeandmaps.shared.util.*
import com.google.android.libraries.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val getItemsForLocationUseCase: GetItemsForLocationUseCase,
) : ViewModel() {

    private val cars = MutableStateFlow(emptyList<Car>())
    private val operationState = MutableStateFlow(idle() as OperationState)

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(CarsViewState())

    val state: StateFlow<CarsViewState>
        get() = _state

    init {
        viewModelScope.launch {
            /*
            Combines the latest value from each of the flows, allowing us to generate a
            view state instance which only contains the latest values.
            */
            combine(
                cars,
                operationState,
            ) { cars, operationState ->
                CarsViewState(
                    cars = cars,
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
            val result = getItemsForLocationUseCase(newBounds)
            cars.value = if (result is Result.Success) {
                operationState.value = success()
                result.value
            } else {
                operationState.value = errorState()
                emptyList()
            }
        }
    }

}

data class CarsViewState(
    val cars: List<Car> = emptyList(),
    val operationState: OperationState = idle(),
)