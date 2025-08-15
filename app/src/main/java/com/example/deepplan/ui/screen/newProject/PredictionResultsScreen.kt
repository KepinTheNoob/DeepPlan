package com.example.deepplan.ui.screen.newProject

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deepplan.R
import com.example.deepplan.data.Screen
import com.example.deepplan.ui.theme.Typography

@Composable
fun PredictionResultsScreen(
    viewModel: NewProjectViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    var showPredictionResultsContent by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.predictionCompleted) {
        Log.d("Loading Prediction", "Prediction selesai: ${uiState.predictionCompleted}")

        if (uiState.predictionCompleted) {
            showPredictionResultsContent = true
        }
    }

    when {
        !(uiState.predictionCompleted) -> {
            CircularProgressIndicator()
        Log.d("Loading Prediction", "Prediction dalam loading: ${uiState.predictionCompleted}")
            Log.d("Loading Prediction", "Result content dalam loading: ${showPredictionResultsContent}")
        }
        showPredictionResultsContent && uiState.predictionCompleted -> {
            PredictionResultsContent(
                viewModel,
                isPredictionPositive = false
            )
        }
    }
}

@Composable
fun PredictionResultsContent(
    viewModel: NewProjectViewModel,
    isPredictionPositive: Boolean,
) {
    val uiState by viewModel.uiState.collectAsState()
    var scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    "Prediction Results",
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = 19.dp,
                        top = 22.dp,
                    )
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            ) {
                if (isPredictionPositive) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_check_24),
                        contentDescription = "Good Result",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(135.dp)
                    )

                    Text(
                        "We think it’s a good offer!",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.baseline_close_24),
                        contentDescription = "Bad Result",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(135.dp)
                    )

                    Text(
                        "We think it’s a bad offer!",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Text(
                "Here is our prediction regarding the offer:",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(
                        top = 30.dp,
                        start = 19.dp,
                    )
            )

            Text(
                "Estimated Final Cost: Rp ${String.format("%.2f", uiState.biaya_akhir_riil_miliar_rp)}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(
                        top = 13.dp,
                        start = 19.dp,
                    )
            )

            Text(
                "Estimated Duration: ${uiState.durasi_akhir_riil_hari} Days",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        start = 19.dp,
                    )
            )

            Text(
                "Profit Margin: ${uiState.profit_margin_riil_persen}%",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        start = 19.dp,
                    )
            )

            Text(
                "Significant Inflation Cost: ${uiState.terjadi_keterlambatan_signifikan}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        start = 19.dp,
                    )
            )

            Text(
                "Significant Late Duration: ${uiState.terjadi_pembengkakan_biaya_signifikan}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        start = 19.dp,
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //                .border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary))
                    .padding(start = 19.dp, end = 19.dp, top = 19.dp)
            ) {
                Text(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed id ligula ut tellus posuere vulputate. Donec pulvinar, mi sit amet consectetur sodales, augue orci posuere sem, quis hendrerit neque erat vitae dolor. Duis maximus sollicitudin sagittis. Sed vestibulum, leo vel lacinia tincidunt, nisi purus vestibulum leo, in pretium est sem quis nisl. Praesent ipsum sem, molestie in bibendum a, lacinia ac nibh. Donec magna dolor, bibendum vel arcu sit amet, placerat mattis nisi. Quisque eget posuere libero. Nulla a dolor convallis, condimentum felis vel, tincidunt eros.",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary))
                        .padding(19.dp)

                )
            }

            Spacer(modifier = Modifier.height(120.dp).fillMaxWidth().background(Color.Transparent))

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier
                    .padding(start = 40.dp, bottom = 24.dp, top = 24.dp)
            ) {
                Text(
                    "Edit Input",
                    style = Typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Button(
                onClick = { /*TODO*/ },
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .padding(end = 19.dp, bottom = 24.dp, top = 24.dp)
            ) {
                Text(
                    "Go to Dashboard",
                    style = Typography.labelLarge,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PredictionResultsScreenPreview() {
    PredictionResultsContent(
        viewModel = viewModel(),
        isPredictionPositive = true,
    )
}
