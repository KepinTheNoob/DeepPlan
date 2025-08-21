package com.example.deepplan.ui.screen.projectDashboardScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deepplan.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deepplan.data.ProjectViewModel
import com.example.deepplan.ui.theme.DeepPlanTheme

@Composable
fun ProjectDashboardScreen(
    viewModel: ProjectDashboardViewModel,
    projectViewModel: ProjectViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val projectUiState by projectViewModel.uiState.collectAsState()
    var showDashboard by remember { mutableStateOf(false) }
    var showPlan by remember { mutableStateOf(false) }


    LaunchedEffect(uiState.isFetchingDataCompleted) {
        viewModel.initDashboard(
            uiState.projectId,
            projectUiState.projects,
        )

        Log.d("Loading Projects", "is fetching data compplete: ${uiState.isFetchingDataCompleted}")
        Log.d("Loading Projects", "greetings: ${uiState.greetings}")
        if (uiState.isFetchingDataCompleted && uiState.greetings != "") {
            Log.d("Loading Projects", "${uiState.tasks}")
            if (uiState.tasks.isNotEmpty()) {
                showPlan = true
            }
            showDashboard = true
        }

    }

    LaunchedEffect(uiState.isGeneratePlanningCompleted) {
//        Log.d("ProjectDashboardScreen", "${uiState.tasks}")
//        if (uiState.tasks.size > 0) {
//            showPlan = true
//        }

        if (uiState.isGeneratePlanningCompleted) {
            showPlan = true
            Log.d("ProjectDashboardScreen", "Planning generated")
        }

    }

    LaunchedEffect(uiState.isButtonRegeneratePlanningClicked) {
        Log.d("Regenerate", "is button clicked: ${uiState.isButtonRegeneratePlanningClicked}")
        if (uiState.isButtonRegeneratePlanningClicked) {
            projectViewModel.updateProjectProgress(uiState.projectId, 0f)
            Log.d("Regenerate", "Regenerate button clicked")
            showPlan = false
            showDashboard = false
        }
//        else {
//            showPlan = true
//            showDashboard = true
//        }
    }

    when {
        showPlan -> {
            Log.d("Loading Projects", "masuk ke showPlan")
            Log.d("Debug Loading", "masuk ke showPlan")

            Log.d("ProjectDashboardScreen", "Plan loaded")
            Home(
                viewModel = viewModel,
                projectViewModel = projectViewModel,
                showPlan = true,
                navController = navController,
            )
        }

        showDashboard && !showPlan -> {
            Log.d("Loading Projects", "masuk ke showDashboard")
            Log.d("Debug Loading", "masuk ke showDashboard")
            Log.d("ProjectDashboardScreen", "Dashboard loaded")
            Home(
                viewModel = viewModel,
                projectViewModel = projectViewModel,
                showPlan = false,
                navController = navController,
            )
        }

        else -> {
            CircularProgressIndicator()
        }
    }

}

@Composable
fun Home(
    viewModel: ProjectDashboardViewModel,
    projectViewModel: ProjectViewModel,
    showPlan: Boolean,
    navController: NavHostController = rememberNavController(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var textBox by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(bottom = 40.dp, start = 23.dp, top = 6.dp)
        ) {
            Text(
                text = uiState.greetings,
                style = TextStyle(
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column (
            modifier = Modifier
                .padding(horizontal = 13.dp, vertical = 16.dp)
                .background(MaterialTheme.colorScheme.onPrimary),
        ) {
            Column (
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = uiState.projectName,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                ElevatedCard (
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column (
                        modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 18.dp, end = 20.dp)
                    ) {
                        Text(
                            text = "Project progress",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.surfaceTint,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        )

                        Spacer(
                            modifier = Modifier.height(27.dp)
                        )

                        LinearProgressIndicator(
                            progress = { uiState.progress },
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "${(uiState.progress * 100).toInt()}%",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(27.dp))

                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp,
                )

                Spacer(modifier = Modifier.height(22.dp))

//                MyTooltipExample()

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Column (
                        modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 18.dp, end = 20.dp)
                    ) {
                        Text(
                            text = "Tip",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Consider giving a well detailed instructions on what the project is about.",
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "I understand",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Right,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(49.dp)
                )

                Text(
                    text = "Summary",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                ExpandableBoxGeneralInformation(uiState)
                ExpandableBoxTechnicalScope(uiState)
                ExpandableBoxExternalContext(uiState)
                ExpandableBoxInternalFactors(uiState)
                ExpandableBoxPredictionResults(uiState)

                Spacer(modifier = Modifier.height(30.dp))

                Log.d("ProjectDashboardScreen", "showPlan = " + showPlan.toString())
                Log.d("ProjectDashboardScreen", "not empty = " + uiState.tasks.isNotEmpty().toString())
                if (uiState.tasks.isEmpty() && !(uiState.isButtonGeneratePlanningClicked)) {
                    Column {
                        Text(
                            text = "Planning",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = "Describe to us how do you want the planning to be laid out:"
                        )

                        Spacer(modifier = Modifier.height(11.dp))

                        TextField(
                            value = textBox,
                            onValueChange = { textBox = it }
                        )
                        Button(
                            onClick = {
                                viewModel.generateTasks(textBox)
                            }
                        ) {
                            Text(text = "Generate Planning")
                        }
                    }
                } else if (!showPlan) {
                    CircularProgressIndicator()
                } else if (showPlan && uiState.tasks.isNotEmpty()) {
                    GanttChartComposable(uiState.tasks)

                    Spacer(modifier = Modifier.height(26.dp))

                    Card (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column (
                            modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 18.dp, end = 20.dp)
                        ) {
                            Text(
                                text = "To-do list",
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Spacer(modifier = Modifier.height(22.dp))

                            Text(
                                text = "A dialog is a type of modal window that appears in front of app content to provide critical information, or prompt for a decision to be made."
                            )

                            Spacer(modifier = Modifier.height(40.dp))

                            CheckList(
                                viewModel = viewModel,
                                projectViewModel = projectViewModel,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(55.dp))

                    Text(
                        text = "Describe to us how do you want the planning to be laid out:"
                    )

                    Spacer(modifier = Modifier.height(11.dp))

                    TextField(
                        value = textBox,
                        onValueChange = { textBox = it }
                    )
                    Button(
                        onClick = {
                            viewModel.generateTasks(textBox, true)
                        }
                    ) {
                        Text(text = "Regenerate Planning")
                    }

                }
            }
        }
    }
}

@Composable
fun ExpandableBoxGeneralInformation(
    uiState: ProjectDashboardUiState
) {
    var expanded by remember { mutableStateOf(false) }

    // Animate arrow rotation
    val rotation by animateFloatAsState(if (expanded) 90f else 0f, label = "arrowRotation")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded } // Toggle dropdown
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.play_arrow_filled),
                contentDescription = "Arrow",
                modifier = Modifier.rotate(rotation)
            )

            Spacer(modifier = Modifier.width(13.dp))

            Text(
                text = "General Information",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Animated dropdown content
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(start = 37.dp, top = 8.dp)) {
                Text(
                    text = "Project Name: ${uiState.projectName}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Project Type: ${uiState.projectType}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Client Type: ${uiState.clientType}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Contract Type: ${uiState.contractType}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Is It Design And Build: ${uiState.isItDesignAndBuild}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Number Of Main Tasks: ${uiState.numberOfMainTasks}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun ExpandableBoxTechnicalScope(
    uiState: ProjectDashboardUiState
) {
    var expanded by remember { mutableStateOf(false) }

    // Animate arrow rotation
    val rotation by animateFloatAsState(if (expanded) 90f else 0f, label = "arrowRotation")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded } // Toggle dropdown
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.play_arrow_filled),
                contentDescription = "Arrow",
                modifier = Modifier.rotate(rotation)
            )

            Spacer(modifier = Modifier.width(13.dp))

            Text(
                text = "Technical Scope",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Animated dropdown content
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(start = 37.dp, top = 8.dp)) {
                Text(
                    text = "Initial Contract Value: ${uiState.initialContractValue}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Estimated Total Man Hours: ${uiState.estimatedTotalManHours}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Earthwork Volume: ${uiState.earthworkVolume}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Concrete Volume: ${uiState.concreteVolume}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Structural Steel Weight: ${uiState.structuralSteelWeight}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Main Installation Length: ${uiState.mainInstallationLength}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Number Of Installation Endpoint: ${uiState.numberOfInstallationEndpoint}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}
@Composable
fun ExpandableBoxExternalContext(
    uiState: ProjectDashboardUiState
) {
    var expanded by remember { mutableStateOf(false) }

    // Animate arrow rotation
    val rotation by animateFloatAsState(if (expanded) 90f else 0f, label = "arrowRotation")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded } // Toggle dropdown
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.play_arrow_filled),
                contentDescription = "Arrow",
                modifier = Modifier.rotate(rotation)
            )

            Spacer(modifier = Modifier.width(13.dp))

            Text(
                text = "External Context",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Animated dropdown content
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(start = 37.dp, top = 8.dp)) {
                Text(
                    text = "Location: ${uiState.location}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Area Type: ${uiState.areaType}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Season: ${uiState.season}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Geotechnical Risk Level: ${uiState.geotechnicalRiskLevel}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Commodity Price Index: ${uiState.commodityPriceIndex}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Number Of Tender Competitor: ${uiState.numberOfTenderCompetitor}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun ExpandableBoxInternalFactors(
    uiState: ProjectDashboardUiState
) {
    var expanded by remember { mutableStateOf(false) }

    // Animate arrow rotation
    val rotation by animateFloatAsState(if (expanded) 90f else 0f, label = "arrowRotation")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded } // Toggle dropdown
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.play_arrow_filled),
                contentDescription = "Arrow",
                modifier = Modifier.rotate(rotation)
            )

            Spacer(modifier = Modifier.width(13.dp))

            Text(
                text = "Internal Factors",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Animated dropdown content
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(start = 37.dp, top = 8.dp)) {
                Text(
                    text = "Project Manager Experience Years: ${uiState.projectManagerExperienceYears}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Core Team Size: ${uiState.coreTeamSize}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Subcontractor Percentage: ${uiState.subcontractorPercentage}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun ExpandableBoxPredictionResults(
    uiState: ProjectDashboardUiState
) {
    var expanded by remember { mutableStateOf(false) }

    // Animate arrow rotation
    val rotation by animateFloatAsState(if (expanded) 90f else 0f, label = "arrowRotation")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded } // Toggle dropdown
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.play_arrow_filled),
                contentDescription = "Arrow",
                modifier = Modifier.rotate(rotation)
            )

            Spacer(modifier = Modifier.width(13.dp))

            Text(
                text = "Prediction Results",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Animated dropdown content
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(start = 37.dp, top = 8.dp)) {
                Text(
                    text = "Estimated Final Cost: Rp ${uiState.biaya_akhir_riil_miliar_rp}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Estimated Duration: ${uiState.durasi_akhir_riil_hari} days",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Profit Margin: ${uiState.profit_margin_riil_persen}%",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Significant Late Duration: ${uiState.terjadi_keterlambatan_signifikan}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Significant Inflation Cost: ${uiState.terjadi_pembengkakan_biaya_signifikan}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MyTooltipExample() {
//    TooltipBox(
//        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
//        tooltip = {
//            RichTooltip {
//                Text("Test 1")
//            }
//        },
//        state = rememberTooltipState()
//    ) {
//        Text("Test 2")
//    }
//}


@Composable
fun CheckList(
    viewModel: ProjectDashboardViewModel,
    projectViewModel: ProjectViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    val tasks = uiState.tasks

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        tasks.forEachIndexed { index, item ->
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                )

                Checkbox(
                    checked = if (item.finished == "yes") true else false,
                    onCheckedChange = {
                        viewModel.setFinishedTask(index, it)

                        Log.d("Update project", "On dashbaord progress: ${viewModel.getProgress()}")
                        projectViewModel.updateProjectProgress(
                            projectId = uiState.projectId,
                            progress = viewModel.getProgress()
                        )
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = Color.White
                    )
                )
            }

            if(index != tasks.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider(color = Color.Gray.copy(alpha = 0.4f), thickness = 1.dp)

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(42.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 28.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Add",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {  }
            )

            Spacer(modifier = Modifier.width(47.dp))

            Text(
                text = "Edit",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {  }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = false) // remove the forced white background
@Composable
fun HomePreview() {
    DeepPlanTheme { // wrap in your app theme
        ProjectDashboardScreen(
            viewModel(),
            viewModel()
        )
    }
}
