package com.example.deepplan.ui.screen.newProject

import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deepplan.data.Screen
import com.example.deepplan.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechnicalScopeScreen(
    viewModel: NewProjectViewModel,
    navController: NavController = rememberNavController(),
    ) {
    val uiState by viewModel.uiState.collectAsState()

    var scrollState = rememberScrollState()
    var initialContractValue by remember { mutableStateOf(uiState.initialContractValue.toString()) }
    var estimatedTotalManHours by remember { mutableStateOf(uiState.estimatedTotalManHours.toString()) }
    var earthworkVolume by remember { mutableStateOf(uiState.earthworkVolume.toString()) }
    var concreteVolume by remember { mutableStateOf(uiState.concreteVolume.toString()) }
    var structuralSteelWeight by remember { mutableStateOf(uiState.structuralSteelWeight.toString()) }
    var mainInstallationLength by remember { mutableStateOf(uiState.mainInstallationLength.toString()) }
    var numberOfInstallationEndpoint by remember { mutableStateOf(uiState.numberOfInstallationEndpoint.toString()) }
    var showWarningDialog by remember {mutableStateOf(false)}
    var warningMessage by remember {mutableStateOf("")}

    fun validateFields(): Boolean {
        val values = listOf(
            initialContractValue,
            estimatedTotalManHours,
            earthworkVolume,
            concreteVolume,
            structuralSteelWeight,
            mainInstallationLength,
            numberOfInstallationEndpoint
        )

        values.forEachIndexed { index, v ->
            val num = v.toFloatOrNull() ?: 0f
            if (num == 0f) {
                warningMessage = when (index) {
                    0 -> "Initial Contract Value cannot be 0 or empty."
                    1 -> "Estimated Total Man Hours cannot be 0 or empty."
                    2 -> "Earthwork Volume cannot be 0 or empty."
                    3 -> "Concrete Volume cannot be 0 or empty."
                    4 -> "Structural Steel Weight cannot be 0 or empty."
                    5 -> "Main Installation Length cannot be 0 or empty."
                    6 -> "Number of Installation Endpoints cannot be 0 or empty."
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
            StepIndicator(totalSteps = 4, currentStep = 2)
            Text(
                "Technical Scope",
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
                    "Initial Contract Value",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                Text(
                    "Enter the initial contract value or tender ceiling in billions of Rupiah (IDR). For example, enter 120.5 for Rp 120,500,000,000.",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                TextField(
                    value = initialContractValue,
                    onValueChange = {
                        try {
                            it.toFloat()
                            initialContractValue = it
                        } catch (e: NumberFormatException) {
                            initialContractValue = ""
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
                    "Estimated Total Man Hours",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                TextField(
                    value = estimatedTotalManHours,
                    onValueChange = {
                        try {
                            it.toFloat()
                            estimatedTotalManHours = it
                        } catch (e: NumberFormatException) {
                            estimatedTotalManHours = ""
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
                    "Earthwork Volume (m³)",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                Text(
                    "Enter the total volume of earthworks (excavation and backfill) in cubic meters (m³). Crucial for infrastructure and foundation work.",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )


                TextField(
                    value = earthworkVolume,
                    onValueChange = {
                        try {
                            it.toFloat()
                            earthworkVolume = it
                        } catch (e: NumberFormatException) {
                            earthworkVolume = ""
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
                    "Concrete Volume (m³)",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                Text(
                    "Enter the total volume of concrete required in cubic meters (m³).",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )


                TextField(
                    value = concreteVolume,
                    onValueChange = {
                        try {
                            it.toFloat()
                            concreteVolume = it
                        } catch (e: NumberFormatException) {
                            concreteVolume = ""
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
                    "Structural Steel Weight (Ton)",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                Text(
                    "Enter the total weight of structural steel (not rebar) in metric tons.",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )


                TextField(
                    value = structuralSteelWeight,
                    onValueChange = {
                        try {
                            it.toFloat()
                            structuralSteelWeight = it
                        } catch (e: NumberFormatException) {
                            structuralSteelWeight = ""
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
                    "Main Installation Length (Km)",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )
                Text(
                    "For linear projects (roads, pipes, cables), enter the total primary installation length in kilometers (km). Use 0 for building-centric projects.",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )
                TextField(
                    value = mainInstallationLength,
                    onValueChange = {
                        try {
                            it.toFloat()
                            mainInstallationLength = it
                        } catch (e: NumberFormatException) {
                            mainInstallationLength = ""
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
                    "Number of Installation Endpoint",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )
                Text(
                    "Enter the total number of endpoints for installations. (e.g., number of CCTV cameras, network data points, light fixtures).",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )
                TextField(
                    value = numberOfInstallationEndpoint,
                    onValueChange = {
                        try {
                            it.toInt()
                            numberOfInstallationEndpoint = it
                        } catch (e: NumberFormatException) {
                            numberOfInstallationEndpoint = ""
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

            Spacer(modifier = Modifier.height(120.dp).fillMaxWidth().background(Color.Transparent))

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
                    viewModel.setTechnicalScopeValues(
                        initialContractValue.toFloat(),
                        estimatedTotalManHours.toFloat(),
                        earthworkVolume.toFloat(),
                        concreteVolume.toFloat(),
                        structuralSteelWeight.toFloat(),
                        mainInstallationLength.toFloat(),
                        numberOfInstallationEndpoint.toInt()
                    )

                    navController.navigate(Screen.NewProjectGeneralInformation.name)
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
                        viewModel.setTechnicalScopeValues(
                            initialContractValue.toFloat(),
                            estimatedTotalManHours.toFloat(),
                            earthworkVolume.toFloat(),
                            concreteVolume.toFloat(),
                            structuralSteelWeight.toFloat(),
                            mainInstallationLength.toFloat(),
                            numberOfInstallationEndpoint.toInt()
                        )
                        navController.navigate(Screen.NewProjectExternalContext.name)
                    } else {
                        showWarningDialog = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier
                    .padding(end = 36.dp, bottom = 24.dp, top = 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Next",
                        style = Typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
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
        AlertDialog(
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
fun TechnicalScopeScreenPreview() {
    TechnicalScopeScreen(
        viewModel = viewModel(),
    )
}