package com.amin.composeandmaps.ui.theme

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppIcon(modifier: Modifier = Modifier, imageVector: ImageVector, tint: Color = blue_1) {
    Icon(
        imageVector = imageVector, contentDescription = null, tint = tint, modifier = modifier
    )
}