package com.example.deepplan.ui.screen.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.deepplan.R

//@Composable
//fun WavyLoading() {
//    val infiniteTransition = rememberInfiniteTransition()
//    val scale by infiniteTransition.animateFloat(
//        initialValue = 0.8f,
//        targetValue = 1.2f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(500, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        )
//    )
//
//    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//        repeat(6) {
//            Image(
//                painter = painterResource(R.drawable.wavy_shape), // Replace with each shape
//                contentDescription = null,
//                modifier = Modifier
//                    .size(40.dp)
//                    .graphicsLayer(scaleX = scale, scaleY = scale)
//            )
//        }
//    }
//}