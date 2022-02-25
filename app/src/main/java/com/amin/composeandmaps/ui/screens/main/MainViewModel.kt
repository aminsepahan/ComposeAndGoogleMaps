package com.amin.composeandmaps.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amin.composeandmaps.data.car.CarRepository
import com.amin.composeandmaps.data.models.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import com.amin.composeandmaps.utils.UIState
import com.amin.composeandmaps.utils.loading
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {

    private val _carsState by lazy { MutableLiveData<UIState<List<Car>>>() }
    val carsState: LiveData<UIState<List<Car>>> = _carsState

    init {
        startApp()
    }

    private fun startApp() {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _carsState.postValue(loading())
            val cars = carRepository.getAllCars()
            _carsState.postValue(UIState.Success(cars))
        }
    }
}