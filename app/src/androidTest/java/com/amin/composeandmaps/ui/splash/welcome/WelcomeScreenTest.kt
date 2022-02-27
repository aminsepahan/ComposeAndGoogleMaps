package com.amin.composeandmaps.ui.splash.welcome

import android.Manifest
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.amin.composeandmaps.MainActivity
import com.amin.composeandmaps.R
import com.amin.composeandmaps.screens.welcome.WelcomeScreen
import com.amin.composeandmaps.screens.welcome.WelcomeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/* You will need a fresh start for this test,
because if the app already had the permissions it will fail */
@RunWith(AndroidJUnit4::class)
class WelcomeScreenTest1NoPermission {

    @get:Rule
    val composeAndroidTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testWelcomeScreenButtonText() {
        val buttonText = composeAndroidTestRule.activity.getString(R.string.btn_give_permissions)
        composeAndroidTestRule.setContent {
            WelcomeScreen(WelcomeViewModel(), {})
        }
        composeAndroidTestRule.onNodeWithText(buttonText).assertExists()
    }

}

@RunWith(AndroidJUnit4::class)
class WelcomeScreenTest2 {

    @get:Rule
    val composeAndroidTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var mRuntimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    @Test
    fun testWelcomeScreenContentText() {
        val text = composeAndroidTestRule.activity.getString(R.string.welcome_text)
        composeAndroidTestRule.setContent {
            WelcomeScreen(WelcomeViewModel(), {})
        }
        composeAndroidTestRule.onNodeWithText(text).assertExists()
    }

    @Test
    fun testWelcomeScreenButtonText() {
        val buttonText = composeAndroidTestRule.activity.getString(R.string.welcome_btn_text)
        composeAndroidTestRule.setContent {
            WelcomeScreen(WelcomeViewModel(), {})
        }
        composeAndroidTestRule.onNodeWithText(buttonText).assertExists()
    }

}
