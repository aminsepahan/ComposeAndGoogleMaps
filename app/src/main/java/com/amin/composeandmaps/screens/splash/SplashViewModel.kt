package com.amin.composeandmaps.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amin.composeandmaps.navigation.NavScreen
import com.amin.composeandmaps.shared.util.Constants.SplashScreenDelay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : ViewModel() {

    private val _startAppNavigation by lazy { MutableLiveData<String>() }
    val startAppNavigation: LiveData<String> = _startAppNavigation

    init {
        startApp()
    }

    private fun startApp() {
        viewModelScope.launch {
            delay(SplashScreenDelay)
            _startAppNavigation.postValue(NavScreen.WelcomeScreen.route)
        }
    }
}