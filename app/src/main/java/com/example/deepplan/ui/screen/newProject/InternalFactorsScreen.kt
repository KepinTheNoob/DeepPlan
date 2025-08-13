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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deepplan.ui.theme.Typography
import com.example.deepplan.R
import com.example.deepplan.data.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InternalFactorsScreen(
    viewModel: NewProjectViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val uiState by viewModel.uiState.collectAsState()

    var scrollState = rememberScrollState()
    var projectManagerExperienceYears by remember { mutableStateOf(uiState.projectManagerExperienceYears.toString()) }
    var coreTeamSize by remember { mutableStateOf(uiState.coreTeamSize.toString()) }
    var subcontractorPercentage by remember { mutableStateOf(uiState.subcontractorPercentage.toString()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 100.dp)
        ) {

            Text(
                "Internal Factors",
                color = MaterialTheme.colorScheme.surface,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 32.dp,
                    top = 22.dp,
                    bottom = 45.dp
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
            ) {
                Text(
                    "Project Manager Experience Years",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                Text(
                    "Enter the total years of relevant experience for the assigned Project Manager. This is a critical human factor for project success.",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                TextField(
                    value = projectManagerExperienceYears,
                    onValueChange = {
                        try {
                            it.toInt()
                            projectManagerExperienceYears = it
                        } catch (e: NumberFormatException) {
                            projectManagerExperienceYears = ""
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
            ) {
                Text(
                    "Core Team Size",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                Text(
                    "Enter the number of core team members (engineers, supervisors) that will be allocated to this project.",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                TextField(
                    value = coreTeamSize,
                    onValueChange = {
                        try {
                            it.toInt()
                            coreTeamSize = it
                        } catch (e: NumberFormatException) {
                            coreTeamSize = ""
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, bottom = 15.dp)
            ) {
                Text(
                    "Subcontractor Percentage (%)",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )

                Text(
                    "Enter the estimated percentage of the total work value that will be outsourced to subcontractors. A high percentage can increase coordination risk.",
                    color = MaterialTheme.colorScheme.surfaceBright,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        bottom = 5.dp
                    )
                )


                TextField(
                    value = subcontractorPercentage,
                    onValueChange = {
                        try {
                            it.toFloat()
                            subcontractorPercentage = it
                        } catch (e: NumberFormatException) {
                            subcontractorPercentage = ""
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp, bottom = 36.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.setInternalContextValues(
                        projectManagerExperienceYears.toInt(),
                        coreTeamSize.toInt(),
                        subcontractorPercentage.toFloat()
                    )

                    navController.navigate(Screen.NewProjectExternalContext.name)
                },
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
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_check_24),
                        contentDescription = "Done",
                        tint = MaterialTheme.colorScheme.surfaceBright
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Done",
                        style = Typography.labelLarge,
                        color = MaterialTheme.colorScheme.surfaceBright
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InternalFactorsScreenPreview() {
    InternalFactorsScreen(
        viewModel = viewModel(),
    )
}
