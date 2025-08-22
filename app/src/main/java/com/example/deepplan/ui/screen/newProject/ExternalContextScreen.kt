package com.example.deepplan.ui.screen.newProject

import android.R
import android.view.RoundedCorner
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deepplan.data.Screen
import com.example.deepplan.ui.theme.Typography
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExternalContextScreen(
    viewModel: NewProjectViewModel,
    navController: NavHostController = rememberNavController(),
    ) {
    val uiState by viewModel.uiState.collectAsState()

    var scrollState = rememberScrollState()
    var selectedLocation by remember { mutableStateOf(uiState.location) }
    var locationExpanded by remember { mutableStateOf(false) }
    val locations = listOf(
        "Kalimantan Timur",
        "Papua Selatan",
        "Lampung",
        "Sumatera Utara",
        "Kepulauan Riau",
        "Kalimantan Selatan",
        "Sumatera Selatan",
        "Riau",
        "Jawa Timur",
        "Sulawesi Selatan",
        "Banten",
        "Papua Tengah",
        "Bali",
        "Papua Pegunungan",
        "Sulawesi Barat",
        "Jawa Tengah",
        "DKI Jakarta",
        "Jawa Barat",
        "Papua Barat",
        "Sulawesi Utara",
        "Papua Barat Daya",
        "Nusa Tenggara Barat",
        "Sulawesi Tenggara",
        "Kalimantan Tengah",
        "Nusa Tenggara Timur",
        "Maluku",
        "Sumatera Barat",
        "Kepulauan Bangka Belitung",
        "Maluku Utara",
        "Sulawesi Tengah",
        "Gorontalo",
        "Papua",
        "Jambi",
        "Aceh",
        "Kalimantan Utara",
        "Kalimantan Barat",
        "Bengkulu",
        "DI Yogyakarta",
    )

    var areaTypeExpanded by remember { mutableStateOf(false) }
    var selectedAreaType by remember { mutableStateOf(uiState.areaType) }
    val areaTypes = listOf(
        "Urban",
        "Sub-urban",
        "Rural",
    )

    var seasonExpanded by remember { mutableStateOf(false) }
    var selectedSeason by remember { mutableStateOf(uiState.season) }
    val seasons = listOf("Hujan", "Kemarau")

    var geotechnicalRiskLevel by remember { mutableFloatStateOf(uiState.geotechnicalRiskLevel.toFloat()) }

    var comodityPriceIndex by remember { mutableStateOf(uiState.commodityPriceIndex.toString()) }

    var numberOfTenderCompetitor by remember { mutableStateOf(uiState.numberOfTenderCompetitor.toString()) }

    var showWarningDialog by remember { mutableStateOf(false) }
    var warningMessage by remember { mutableStateOf("") }

    fun validateFields(): Boolean {
        val values = listOf(comodityPriceIndex, numberOfTenderCompetitor)
        values.forEachIndexed { index, v ->
            val num = v.toFloatOrNull() ?: 0f
            if (num == 0f) {
                warningMessage = when (index) {
                    0 -> "Commodity Price Index cannot be 0 or empty."
                    1 -> "Number of Tender Competitors cannot be 0 or empty."
                    else -> "Please fill all fields."
                }
                return false
            }
        }
        return true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .verticalScroll(scrollState)
        ) {
            StepIndicator(totalSteps = 4, currentStep = 3)
            Text(
                "External Context",
                color = MaterialTheme.colorScheme.surface,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 32.dp,
                    top = 22.dp,
                    bottom = 27.dp
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
            ) {
                Text(
                    "Location",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                ExposedDropdownMenuBox(
                    expanded = locationExpanded,
                    onExpandedChange = { locationExpanded = !locationExpanded }
                ) {
                    val bottomCornerRadius by animateDpAsState(
                        targetValue = if (locationExpanded) 0.dp else 16.dp,
                        animationSpec = tween(durationMillis = 250)
                    )

                    Column {
                        TextField(
                            value = selectedLocation,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 14.sp
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = locationExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = bottomCornerRadius,
                                bottomEnd = bottomCornerRadius
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        if (locationExpanded) {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = locationExpanded,
                        onDismissRequest = { locationExpanded = false },
                        modifier = Modifier.offset(y = 4.dp),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    ) {
                        locations.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(text = type) },
                                onClick = {
                                    selectedLocation = type
                                    locationExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
            ) {
                Text(
                    "Area Type",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                ExposedDropdownMenuBox(
                    expanded = areaTypeExpanded,
                    onExpandedChange = { areaTypeExpanded = !areaTypeExpanded }
                ) {
                    val bottomCornerRadius by animateDpAsState(
                        targetValue = if (areaTypeExpanded) 0.dp else 16.dp,
                        animationSpec = tween(durationMillis = 250)
                    )

                    Column {
                        TextField(
                            value = selectedAreaType,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 14.sp
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = areaTypeExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = bottomCornerRadius,
                                bottomEnd = bottomCornerRadius
                            ),
                            colors = TextFieldDefaults.colors(
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            )
                        )

                        if (areaTypeExpanded) {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = areaTypeExpanded,
                        onDismissRequest = { areaTypeExpanded = false },
                        modifier = Modifier.offset(y = 4.dp),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    ) {
                        areaTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(text = type) },
                                onClick = {
                                    selectedAreaType = type
                                    areaTypeExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
            ) {
                Text(
                    "Season",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                Text(
                    "Select the dominant season during the project's execution phase. The rainy season often leads to delays in outdoor work.",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                ExposedDropdownMenuBox(
                    expanded = seasonExpanded,
                    onExpandedChange = { seasonExpanded = !seasonExpanded }
                ) {
                    val bottomCornerRadius by animateDpAsState(
                        targetValue = if (seasonExpanded) 0.dp else 16.dp,
                        animationSpec = tween(durationMillis = 250)
                    )

                    Column {
                        TextField(
                            value = selectedSeason,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 14.sp
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = seasonExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = bottomCornerRadius,
                                bottomEnd = bottomCornerRadius
                            ),
                            colors = TextFieldDefaults.colors(
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            )
                        )

                        if (seasonExpanded) {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = seasonExpanded,
                        onDismissRequest = { seasonExpanded = false },
                        modifier = Modifier.offset(y = 4.dp),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    ) {
                        seasons.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(text = type) },
                                onClick = {
                                    selectedSeason = type
                                    seasonExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, bottom = 18.dp)
            ) {
                Text(
                    "Geotechnical Risk Level",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                Text(
                    "Based on the soil survey report, rate the geotechnical risk on a scale of 1 (very low) to 5 (very high).",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )
                Slider(
                    value = geotechnicalRiskLevel,
                    onValueChange = { geotechnicalRiskLevel = it },
                    valueRange = 1f..5f,
                    steps = 3,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.secondaryContainer,
                        activeTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                        inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = geotechnicalRiskLevel.roundToInt().toString(),
                    color = MaterialTheme.colorScheme.surface,
                    style = Typography.labelLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
            ) {
                Text(
                    "Comodity Price Index",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )
                Text(
                    "Enter the relevant commodity price index (e.g., steel price index) at the start of the project. This helps the model account for material price volatility.",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                TextField(
                    value = comodityPriceIndex,
                    onValueChange = {
                        try {
                            it.toFloat()
                            comodityPriceIndex = it
                        } catch (e: NumberFormatException) {
                            comodityPriceIndex = ""
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
            ) {
                Text(
                    "Number of Tender Competitor",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )
                Text(
                    "Enter the number of other companies that participated in the tender. A higher number indicates tighter market competition.",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )
                TextField(
                    value = numberOfTenderCompetitor,
                    onValueChange = {
                        try {
                            it.toInt()
                            numberOfTenderCompetitor = it
                        } catch (e: NumberFormatException) {
                            numberOfTenderCompetitor = ""
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .background(Color.Transparent))

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.setExternalContextValues(
                        selectedLocation,
                        selectedAreaType,
                        selectedSeason,
                        geotechnicalRiskLevel.toInt(),
                        comodityPriceIndex.toFloat(),
                        numberOfTenderCompetitor.toInt()
                    )

                    navController.navigate(Screen.NewProjectTechnicalScope.name)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier
                    .padding(start = 36.dp, bottom = 24.dp, top = 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Back",
                        style = Typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            Button(
                onClick = {
                    if (validateFields()) {
                        viewModel.setExternalContextValues(
                            selectedLocation,
                            selectedAreaType,
                            selectedSeason,
                            geotechnicalRiskLevel.toInt(),
                            comodityPriceIndex.toFloat(),
                            numberOfTenderCompetitor.toInt()
                        )
                        navController.navigate(Screen.NewProjectInternalFactors.name)
                    } else {
                        showWarningDialog = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.padding(end = 36.dp, bottom = 24.dp, top = 24.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Next", style = Typography.labelLarge, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }

    if (showWarningDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showWarningDialog = false },
            confirmButton = {
                TextButton(onClick = { showWarningDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Invalid Input") },
            text = { Text(warningMessage) }
        )
    }
}

@Preview(showBackground=true)
@Composable
fun ExternalContextScreenPreview() {
    ExternalContextScreen(
        viewModel = viewModel()
    )
}
