package com.amin.composeandmaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.amin.composeandmaps.ui.theme.ComposeAndMapsTheme
import com.amin.composeandmaps.ui.utils.rememberMapViewWithLifecycle
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.awaitMap
import com.amin.composeandmaps.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            CompositionLocalProvider {
                ComposeAndMapsTheme {
                    AppNavHost(navController)
                }
            }
        }
    }
}

@Composable
fun ActivityContent() {
    ComposeAndMapsTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.White)
        ) {
            val mapView = rememberMapViewWithLifecycle()
            AndroidView({ mapView }) { createdMapView ->
                CoroutineScope(Dispatchers.Main).launch {
                    val map = createdMapView.awaitMap()
                    map.uiSettings.isZoomControlsEnabled = true
                    val destination = LatLng(-32.491, 147.309)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 6f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAndMapsTheme {
        ActivityContent()
    }
}