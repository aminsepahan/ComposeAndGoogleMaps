package com.amin.composeandmaps.ui.screens.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amin.composeandmaps.MainCoroutineRule
import com.amin.composeandmaps.getOrAwaitValueTest
import com.amin.composeandmaps.navigation.NavScreen
import com.amin.composeandmaps.utils.Constants.SplashScreenDelay
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {
    private lateinit var viewModel: SplashViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = SplashViewModel()
    }

    @Test
    fun `check if app is starting`() {
        mainCoroutineRule.runBlockingTest {
            advanceTimeBy(SplashScreenDelay)
            val value = viewModel.startAppNavigation.getOrAwaitValueTest()
            assertThat(value).isEqualTo(NavScreen.WelcomeScreen.route)
        }
    }
}