package com.example.deepplan.ui.screen.newProject

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.deepplan.ui.screen.predictionLoading.ProjectLoadingScreen
import com.example.deepplan.ui.theme.Typography

@Composable
fun PredictionResultsScreen(
    viewModel: NewProjectViewModel,
    navController: NavHostController = rememberNavController(),
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
            // 🔥 Replace default loading with your custom screen
            ProjectLoadingScreen()

            Log.d("Loading Prediction", "Prediction dalam loading: ${uiState.predictionCompleted}")
            Log.d("Loading Prediction", "Result content dalam loading: $showPredictionResultsContent")
        }
        showPredictionResultsContent && uiState.predictionCompleted -> {
            PredictionResultsContent(
                viewModel,
                navController
            )
        }
    }
}

@Composable
fun getResultMessage(isGood: Boolean): String {
    val goodMessages = listOf(
        "This looks like a strong choice.",
        "We believe this plan has solid potential.",
        "Things are looking very favorable here.",
        "This seems like a promising direction."
    )

    val badMessages = listOf(
        "This option might not be ideal.",
        "We see potential risks in this outcome.",
        "This doesn’t look very favorable.",
        "You may want to reconsider this path."
    )

    return if (isGood) {
        goodMessages.random()
    } else {
        badMessages.random()
    }
}

@Composable
fun PredictionResultsContent(
    viewModel: NewProjectViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            // 🔹 Header
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

            // 🔹 Status Section with Icon
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 20.dp)
            ) {
                if (uiState.goodPrediction) {
                    Image(
                        painter = painterResource(id = R.drawable.checkroundedwhite),
                        contentDescription = "Good Offer",
                        modifier = Modifier.size(76.dp)
                            .padding(bottom = 10.dp)
                    )
                    Text(
                        "Good Offer",
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.crossroundedwhite),
                        contentDescription = "Bad Offer",
                        modifier = Modifier.size(76.dp)
                            .padding(bottom = 10.dp)
                    )
                    Text(
                        "Bad Offer",
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Text(
                    getResultMessage(uiState.goodPrediction),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }

            // 🔹 Prediction Details Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.08f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Details",
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    // 🔹 KEEPING YOUR EXACT LINES
                    Text(
                        "Estimated Final Cost: Rp ${String.format("%.2f", uiState.biaya_akhir_riil_miliar_rp)}",
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 13.dp, start = 19.dp)
                    )

                    Text(
                        "Estimated Duration: ${uiState.durasi_akhir_riil_hari} Days",
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 10.dp, start = 19.dp)
                    )

                    Text(
                        "Profit Margin: ${uiState.profit_margin_riil_persen}%",
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 10.dp, start = 19.dp)
                    )

                    Text(
                        "Significant Inflation Cost: ${uiState.terjadi_keterlambatan_signifikan}",
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 10.dp, start = 19.dp)
                    )

                    Text(
                        "Significant Late Duration: ${uiState.terjadi_pembengkakan_biaya_signifikan}",
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 10.dp, start = 19.dp)
                    )
                }
            }

            // 🔹 Reason Box styled
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.surface),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    uiState.reason,
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
        }

        // 🔹 Bottom buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.setPredictionCompleted(false)
                    navController.navigate(Screen.NewProjectGeneralInformation.name)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.padding(start = 40.dp, bottom = 24.dp, top = 24.dp)
            ) {
                Text(
                    "Edit Input",
                    style = Typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Button(
                onClick = {
                    viewModel.setPredictionCompleted(false)
                    navController.navigate(Screen.Home.name)
                },
                modifier = Modifier.padding(end = 19.dp, bottom = 24.dp, top = 24.dp)
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
    )
}
