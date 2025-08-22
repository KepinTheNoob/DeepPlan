package com.example.deepplan.ui.screen.newProject

import android.util.Log
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
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deepplan.data.Screen
import com.example.deepplan.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun GeneralInformationScreen(
    viewModel: NewProjectViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val uiState by viewModel.uiState.collectAsState()

    var scrollState = rememberScrollState()
    var projectName by remember { mutableStateOf(uiState.projectName) }
    var numberOfMainTasks by remember { mutableStateOf(uiState.numberOfMainTasks.toString()) }
    var designAndBuildExpanded by remember { mutableStateOf(false) }
    var selectedDesignAndBuild by remember { mutableStateOf(
        if (uiState.isItDesignAndBuild) "Yes" else "No"
    ) }
    val designAndBuildOptions = listOf("Yes", "No")
    var projectTypeExpanded by remember { mutableStateOf(false) }
    var selectedProjectType by remember { mutableStateOf(uiState.projectType) }
    val projectTypes = listOf(
        "Perumahan",
        "Jalan Tol",
        "Jaringan IT",
        "Gedung Perkantoran",
        "Jembatan",
        "Pabrik",
    )
    var clientTypeExpanded by remember { mutableStateOf(false) }
    var selectedClientType by remember { mutableStateOf(uiState.clientType) }
    val clientTypes = listOf(
        "BUMN",
        "Swasta Asing",
        "Swasta Nasional",
        "Pemda",
    )
    var contractTypeExpanded by remember { mutableStateOf(false) }
    var selectedContractType by remember { mutableStateOf(uiState.contractType) }
    val contractTypes = listOf(
        "Lump Sum",
        "Cost Plus",
        "Harga Satuan",
    )
    var showDialog by remember {mutableStateOf(false)}
    var dialogMessage by remember {mutableStateOf("")}

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
            StepIndicator(totalSteps = 4, currentStep = 1)

            Text(
                "General Information",
                color = MaterialTheme.colorScheme.surface,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 32.dp,
                    top = 22.dp,
                    bottom = 26.dp
                )
            )

            Column(
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
                        bottom = 7.dp
                    )
                )

                TextField(
                    value = projectName,
                    onValueChange = { projectName = it },
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
                    "Project Type",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 7.dp
                    )
                )

                ExposedDropdownMenuBox(
                    expanded = projectTypeExpanded,
                    onExpandedChange = { projectTypeExpanded = !projectTypeExpanded }
                ) {
                    val bottomCornerRadius by animateDpAsState(
                        targetValue = if (projectTypeExpanded) 0.dp else 16.dp,
                        animationSpec = tween(durationMillis = 250)
                    )

                    Column {
                        TextField(
                            value = selectedProjectType,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 14.sp
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = projectTypeExpanded)
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
                        
                        if (projectTypeExpanded) {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = projectTypeExpanded,
                        onDismissRequest = { projectTypeExpanded = false },
                        modifier = Modifier.offset(y = 4.dp),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
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
            Column(
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
                        bottom = 7.dp
                    )
                )

                ExposedDropdownMenuBox(
                    expanded = clientTypeExpanded,
                    onExpandedChange = { clientTypeExpanded = !clientTypeExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = selectedClientType,
                        onValueChange = {},
                        readOnly = true,
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 14.sp
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = clientTypeExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = if (clientTypeExpanded) {
                            RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                        } else {
                            RoundedCornerShape(16.dp)
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        )
                    )

                    ExposedDropdownMenuBox(
                        expanded = clientTypeExpanded,
                        onExpandedChange = { clientTypeExpanded = !clientTypeExpanded }
                    ) {
                        val bottomCornerRadius by animateDpAsState(
                            targetValue = if (clientTypeExpanded) 0.dp else 16.dp,
                            animationSpec = tween(durationMillis = 250)
                        )

                        Column {
                            TextField(
                                value = selectedClientType,
                                onValueChange = {},
                                readOnly = true,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 14.sp
                                ),
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = clientTypeExpanded)
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

                            if (clientTypeExpanded) {
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }

                        ExposedDropdownMenu(
                            expanded = clientTypeExpanded,
                            onDismissRequest = { clientTypeExpanded = false },
                            modifier = Modifier.offset(y = 4.dp),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            )
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
            }
            Column(
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
                        bottom = 7.dp
                    )
                )

                ExposedDropdownMenuBox(
                    expanded = contractTypeExpanded,
                    onExpandedChange = { contractTypeExpanded = !contractTypeExpanded }
                ) {
                    val bottomCornerRadius by animateDpAsState(
                        targetValue = if (contractTypeExpanded) 0.dp else 16.dp,
                        animationSpec = tween(durationMillis = 250)
                    )

                    Column {
                        TextField(
                            value = selectedContractType,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 14.sp
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = contractTypeExpanded)
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

                        if (contractTypeExpanded) {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = contractTypeExpanded,
                        onDismissRequest = { contractTypeExpanded = false },
                        modifier = Modifier.offset(y = 4.dp),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
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

            Column(
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
                        bottom = 7.dp
                    )
                )

                ExposedDropdownMenuBox(
                    expanded = designAndBuildExpanded,
                    onExpandedChange = { designAndBuildExpanded = !designAndBuildExpanded }
                ) {
                    val bottomCornerRadius by animateDpAsState(
                        targetValue = if (designAndBuildExpanded) 0.dp else 16.dp,
                        animationSpec = tween(durationMillis = 250)
                    )

                    Column {
                        TextField(
                            value = selectedDesignAndBuild,
                            onValueChange = {},
                            readOnly = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 14.sp
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = designAndBuildExpanded)
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

                        if (designAndBuildExpanded) {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = designAndBuildExpanded,
                        onDismissRequest = { designAndBuildExpanded = false },
                        modifier = Modifier.offset(y = 4.dp),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
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
            Column(
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
                        bottom = 7.dp
                    )
                )
                TextField(
                    value = numberOfMainTasks,
                    onValueChange = {
                        try {
                            it.toInt()
                            numberOfMainTasks = it
                        } catch (e: NumberFormatException) {
                            numberOfMainTasks = ""
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                onClick = { /*TODO*/ },
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
                    val num = numberOfMainTasks.toIntOrNull()

                    when {
                        projectName.isBlank() -> {
                            dialogMessage = "Project name cannot be empty."
                            showDialog = true
                        }
                        selectedProjectType.isBlank() -> {
                            dialogMessage = "Please select a project type."
                            showDialog = true
                        }
                        selectedClientType.isBlank() -> {
                            dialogMessage = "Please select a client type."
                            showDialog = true
                        }
                        selectedContractType.isBlank() -> {
                            dialogMessage = "Please select a contract type."
                            showDialog = true
                        }
                        num == null || num <= 0 -> {
                            dialogMessage = "Number of main tasks must be greater than zero."
                            showDialog = true
                        }
                        else -> {
                            viewModel.setGeneralInformationValues(
                                projectName,
                                selectedProjectType,
                                selectedClientType,
                                selectedContractType,
                                if (selectedDesignAndBuild == "Yes") true else false,
                                num
                            )
                            navController.navigate(Screen.NewProjectTechnicalScope.name)
                        }
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
        if (showDialog) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Invalid Input") },
                text = { Text(dialogMessage) }
            )
        }
    }
}

@Preview(showBackground=true)
@Composable
fun GeneralInformationScreenPreview() {
    GeneralInformationScreen(viewModel = viewModel())
}