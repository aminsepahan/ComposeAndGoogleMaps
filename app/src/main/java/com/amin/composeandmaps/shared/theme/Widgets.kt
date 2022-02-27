package com.amin.composeandmaps.shared.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    backgroundColor: Color = grey_1,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        backgroundColor = backgroundColor,
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        content = content
    )
}