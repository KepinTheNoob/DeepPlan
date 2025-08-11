package com.example.deepplan.ui.screen.newProject

import androidx.compose.foundation.background
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
fun GeneralInformationScreen() {
    var scrollState = rememberScrollState()
    var projectName by remember { mutableStateOf("") }
    var numberOfMainTasks by remember { mutableStateOf("") }
    var designAndBuildExpanded by remember { mutableStateOf(false) }
    var selectedDesignAndBuild by remember { mutableStateOf("Yes") }
    val designAndBuildOptions = listOf("Yes", "No")
    var projectTypeExpanded by remember { mutableStateOf(false) }
    var selectedProjectType by remember { mutableStateOf("Personal") }
    val projectTypes = listOf("Personal", "Work", "School", "Hobby")
    var clientTypeExpanded by remember { mutableStateOf(false) }
    var selectedClientType by remember { mutableStateOf("Individual") }
    val clientTypes = listOf("Individual", "Company", "Non-profit")
    var contractTypeExpanded by remember { mutableStateOf(false) }
    var selectedContractType by remember { mutableStateOf("Fixed-price") }
    val contractTypes = listOf("Fixed-price", "Time and materials", "Retainer")

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .verticalScroll(scrollState)
    ) {

        Text(
            "General Information",
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
                "Project Name",
                color = MaterialTheme.colorScheme.surfaceBright,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(
                    bottom = 5.dp
                )
            )

            TextField(
                value = projectName,
                onValueChange = {projectName = it},
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
                "Project Type",
                color = MaterialTheme.colorScheme.surfaceBright,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(
                    bottom = 5.dp
                )
            )

            ExposedDropdownMenuBox(
                expanded = projectTypeExpanded,
                onExpandedChange = { projectTypeExpanded = !projectTypeExpanded }
            ) {
                TextField(
                    value = selectedProjectType,
                    onValueChange = {},
                    readOnly = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp
                    ),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = projectTypeExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth().height(48.dp)
                )

                ExposedDropdownMenu(
                    expanded = projectTypeExpanded,
                    onDismissRequest = { projectTypeExpanded = false }
                ) {
                    projectTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type) },
                            onClick = {
                                selectedProjectType = type
                                projectTypeExpanded = false
                            }
                        )
                    }
                }
            }
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
        ) {
            Text(
                "Client Type",
                color = MaterialTheme.colorScheme.surfaceBright,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(
                    bottom = 5.dp
                )
            )

            ExposedDropdownMenuBox(
                expanded = clientTypeExpanded,
                onExpandedChange = { clientTypeExpanded = !clientTypeExpanded }
            ) {
                TextField(
                    value = selectedClientType,
                    onValueChange = {},
                    readOnly = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp
                    ),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = clientTypeExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth().height(48.dp)
                )

                ExposedDropdownMenu(
                    expanded = clientTypeExpanded,
                    onDismissRequest = { clientTypeExpanded = false }
                ) {
                    clientTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type) },
                            onClick = {
                                selectedClientType = type
                                clientTypeExpanded = false
                            }
                        )
                    }
                }
            }
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
        ) {
            Text(
                "Contract Type",
                color = MaterialTheme.colorScheme.surfaceBright,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(
                    bottom = 5.dp
                )
            )

            ExposedDropdownMenuBox(
                expanded = contractTypeExpanded,
                onExpandedChange = { contractTypeExpanded = !contractTypeExpanded }
            ) {
                TextField(
                    value = selectedContractType,
                    onValueChange = {},
                    readOnly = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp
                    ),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = contractTypeExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth().height(48.dp)
                )

                ExposedDropdownMenu(
                    expanded = contractTypeExpanded,
                    onDismissRequest = { contractTypeExpanded = false }
                ) {
                    contractTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type) },
                            onClick = {
                                selectedContractType = type
                                contractTypeExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
        ) {
            Text(
                "Is it Design and Build?",
                color = MaterialTheme.colorScheme.surfaceBright,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(
                    bottom = 5.dp
                )
            )
            Text(
                "Select Yes if your company is responsible for both the design and construction phases under a single contract",
                color = MaterialTheme.colorScheme.surfaceBright,
                fontSize = 12.sp,
                modifier = Modifier.padding(
                    bottom = 5.dp
                )
            )

            ExposedDropdownMenuBox(
                expanded = designAndBuildExpanded,
                onExpandedChange = { designAndBuildExpanded = !designAndBuildExpanded }
            ) {
                TextField(
                    value = selectedDesignAndBuild,
                    onValueChange = {},
                    readOnly = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp
                    ),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = designAndBuildExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth().height(48.dp)
                )

                ExposedDropdownMenu(
                    expanded = designAndBuildExpanded,
                    onDismissRequest = { designAndBuildExpanded = false }
                ) {
                    designAndBuildOptions.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type) },
                            onClick = {
                                selectedDesignAndBuild = type
                                designAndBuildExpanded = false
                            }
                        )
                    }
                }
            }
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
        ) {
            Text(
                "Number of Main Tasks",
                color = MaterialTheme.colorScheme.surfaceBright,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(
                    bottom = 5.dp
                )
            )
            Text(
                "Enter the total number of primary line items from the Bill of Quantities (BoQ). This serves as a high-level indicator of the project's complexity.",
                color = MaterialTheme.colorScheme.surfaceBright,
                fontSize = 12.sp,
                modifier = Modifier.padding(
                    bottom = 5.dp
                )
            )
            TextField(
                value = numberOfMainTasks,
                onValueChange = {numberOfMainTasks = it},
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 36.dp, bottom = 36.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
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
fun GeneralInformationScreenPreview() {
    GeneralInformationScreen()
}