package com.example.deepplan.ui.screen.predictionLoading

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deepplan.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CookieMorphingAnimation(
    modifier: Modifier = Modifier
) {
    val cookieShapes = listOf(
        R.drawable.six_sided_cookie,
        R.drawable.seven_sided_cookie,
        R.drawable.eight_sided_cookie,
        R.drawable.nine_sided_cookie,
        R.drawable.twelve_sided_cookie
    )

    var currentIndex by remember { mutableStateOf(0) }
    var goingForward by remember { mutableStateOf(true) }

    val scale = remember { Animatable(1f) }
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            val nextIndex = if (goingForward) {
                if (currentIndex < cookieShapes.lastIndex) currentIndex + 1
                else {
                    goingForward = false
                    currentIndex - 1
                }
            } else {
                if (currentIndex > 0) currentIndex - 1
                else {
                    goingForward = true
                    currentIndex + 1
                }
            }

            // Animate slight rotation (not full 360)
            launch {
                rotation.animateTo(
                    rotation.value + 90f, // 🔥 rotate only 70–90 degrees
                    animationSpec = tween(800, easing = FastOutSlowInEasing)
                )
            }

            // Morphing "pop" effect
            launch {
                scale.animateTo(
                    1.2f,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
                scale.animateTo(
                    1f,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
            }

            // 🔥 Change shape *before rotation finishes*
            delay(300)
            currentIndex = nextIndex

            // wait until animations settle before looping again
            delay(300)
        }
    }

    Image(
        painter = painterResource(id = cookieShapes[currentIndex]),
        contentDescription = "Morphing Cookie Shape",
        modifier = modifier
            .size(175.dp)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                rotationZ = rotation.value
            }
    )
}


@Composable
fun ProjectLoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CookieMorphingAnimation()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Please wait while we summarize\nyour project",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProjectLoadingScreen() {
    ProjectLoadingScreen()
}
