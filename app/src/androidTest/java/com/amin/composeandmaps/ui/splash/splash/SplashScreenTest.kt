package com.amin.composeandmaps.ui.splash.splash

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amin.composeandmaps.MainActivity
import com.amin.composeandmaps.R
import com.amin.composeandmaps.ui.screens.SplashScreenContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule
    val composeAndroidTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testSplashScreenText() {
        val text = composeAndroidTestRule.activity.getString(R.string.splash_text)
        composeAndroidTestRule.onNodeWithText(text).assertExists()
    }

    @Test
    fun testSplashScreenContentText() {
        composeAndroidTestRule.setContent {
            SplashScreenContent()
        }
        val text = composeAndroidTestRule.activity.getString(R.string.splash_text)
        composeAndroidTestRule.onNodeWithText(text).assertExists()
    }


}