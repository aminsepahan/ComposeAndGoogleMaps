package com.amin.composeandmaps.ui.car_list

import android.Manifest
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.amin.composeandmaps.MainActivity
import com.amin.composeandmaps.data.models.Car
import com.amin.composeandmaps.data.models.FleetType
import com.amin.composeandmaps.screens.cars.CarList
import com.amin.composeandmaps.screens.cars.TASK_LIST_TEST_TAG
import com.google.android.libraries.maps.model.LatLng
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarListTest {

    @get:Rule
    val composeAndroidTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var mRuntimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    @Test
    fun test_car_list_empty_list() {
        composeAndroidTestRule.setContent {
            CarList(emptyList(), {})
        }
        composeAndroidTestRule.onNodeWithTag(TASK_LIST_TEST_TAG).onChildren().assertCountEquals(0)
    }

    @Test
    fun test_car_list_count() {
        val cars = getFakeCars()
        composeAndroidTestRule.setContent {
            CarList(cars, {})
        }
        composeAndroidTestRule.onNodeWithTag(TASK_LIST_TEST_TAG).onChildren().assertCountEquals(2)
    }

    @Test
    fun test_car_list_data() {
        val cars = getFakeCars()
        composeAndroidTestRule.setContent {
            CarList(cars, {})
        }
        composeAndroidTestRule.onNodeWithTag(TASK_LIST_TEST_TAG)
            .onChildren()
            .onFirst()
            .assert(hasText("Taxi, Heading: 3.0"))
    }

    fun getFakeCars() = mutableListOf(
        Car(15, LatLng(1.0, 2.0), FleetType.TAXI, 3.0),
        Car(30, LatLng(4.0, 5.0), FleetType.TAXI, 6.0)
    )
}
