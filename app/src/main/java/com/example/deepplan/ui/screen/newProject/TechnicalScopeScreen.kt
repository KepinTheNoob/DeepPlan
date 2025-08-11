package com.example.deepplan.ui.screen.newProject

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deepplan.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechnicalScopeScreen() {
    var scrollState = rememberScrollState()
    var initialContractValue by remember { mutableStateOf("") }
    var estimatedTotalManHours by remember { mutableStateOf("") }
    var earthworkVolume by remember { mutableStateOf("") }
    var concreteVolume by remember { mutableStateOf("") }
    var structuralSteelWeight by remember { mutableStateOf("") }
    var mainInstallationLength by remember { mutableStateOf("") }
    var numberOfInstallationEndpoint by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .verticalScroll(scrollState)
    ) {

        Text(
            "Technical Scope",
            color = MaterialTheme.colorScheme.surface,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                start = 32.dp,
                top = 22.dp,
                bottom = 45.dp
            )
        )

        Column (
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
                onValueChange = {initialContractValue = it},
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }

        Column (
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
                onValueChange = {estimatedTotalManHours = it},
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
        Column (
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
                onValueChange = {earthworkVolume = it},
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
        Column (
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
                onValueChange = {concreteVolume = it},
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }

        Column (
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
                onValueChange = {structuralSteelWeight = it},
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
        Column (
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
                onValueChange = {mainInstallationLength = it},
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }

        Column (
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
                onValueChange = {numberOfInstallationEndpoint = it},
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp).fillMaxWidth().background(Color.Transparent))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp, bottom = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
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
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Next",
                        style = Typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Preview(showBackground=true)
@Composable
fun TechnicalScopeScreenPreview() {
    TechnicalScopeScreen()
}