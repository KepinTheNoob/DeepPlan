package com.example.deepplan.ui.screen.predictionLoading

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deepplan.R
import kotlinx.coroutines.delay

@Composable
fun CookieMorphingAnimation(
    modifier: Modifier = Modifier
) {
    val cookieShapes = listOf(
        R.drawable.four_sided_cookie,
        R.drawable.six_sided_cookie,
        R.drawable.seven_sided_cookie,
        R.drawable.eight_sided_cookie,
        R.drawable.nine_sided_cookie,
        R.drawable.twelve_sided_cookie
    )

    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            currentIndex = (currentIndex + 1) % cookieShapes.size
        }
    }

    Crossfade(
        targetState = cookieShapes[currentIndex],
        animationSpec = tween(
            durationMillis = 100,
            easing = EaseOutBounce
        ),
        modifier = modifier.size(175.dp)
    ) { shapeRes ->
        Image(
            painter = painterResource(id = shapeRes),
            contentDescription = "Morphing Cookie Shape"
        )
    }
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

@Preview(showBackground = true)
@Composable
fun PreviewProjectLoadingScreen() {
    ProjectLoadingScreen()
}