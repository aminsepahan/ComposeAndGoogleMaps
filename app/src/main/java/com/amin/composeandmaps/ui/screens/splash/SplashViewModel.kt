package de.allianzservices.hrdmobile.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.allianzservices.hrdmobile.navigation.NavScreen
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
            delay(500)
            _startAppNavigation.postValue(NavScreen.WelcomeScreen.route)
        }
    }
}