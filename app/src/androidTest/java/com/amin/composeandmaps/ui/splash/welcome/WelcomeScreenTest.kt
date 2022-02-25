package com.amin.composeandmaps.ui.splash.welcome

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amin.composeandmaps.MainActivity
import com.amin.composeandmaps.R
import com.amin.composeandmaps.ui.screens.SplashScreenContent
import com.amin.composeandmaps.ui.screens.welcome.WelcomeScreenContent
import com.amin.composeandmaps.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WelcomeScreenTest {

    @get:Rule
    val composeAndroidTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testWelcomeScreenContentText() {
        composeAndroidTestRule.setContent {
            WelcomeScreenContent {}
        }
        val text = composeAndroidTestRule.activity.getString(R.string.welcome_text)
        composeAndroidTestRule.onNodeWithText(text).assertExists()
    }

    @Test
    fun testWelcomeScreenButtonText() {
        composeAndroidTestRule.setContent {
            WelcomeScreenContent {}
        }
        val text = composeAndroidTestRule.activity.getString(R.string.welcome_text)
        composeAndroidTestRule.onNodeWithText(text).assertExists()
    }


}