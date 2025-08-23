package com.example.deepplan.ui.screen.manageProject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.deepplan.ui.theme.Typography
import com.example.deepplan.R
import com.example.deepplan.data.Project
import com.example.deepplan.data.ProjectOverview
import com.example.deepplan.data.ProjectViewModel
import com.example.deepplan.data.Screen
import com.example.deepplan.ui.screen.projectDashboardScreen.ProjectDashboardViewModel

val Typography = Typography(
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)



@Composable
fun ManageProjectScreen(
    viewModel: ManageProjectViewModel,
    projectViewModel: ProjectViewModel,
    projectDashboardViewModel: ProjectDashboardViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val projectUiState by projectViewModel.uiState.collectAsState()
    var showProjectsCard by remember { mutableStateOf(false) }
    var isEditMode by remember { mutableStateOf(false) }
    var projectToDelete by remember { mutableStateOf<ProjectOverview?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.finishedLoadingProjects) {
        viewModel.loadProjects(
            projectUiState.projects
        )
        Log.d("Loading Projects", "Loading selesai: ${uiState.finishedLoadingProjects}")

        if (uiState.finishedLoadingProjects) {
            showProjectsCard = true
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Section - The purple header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f) // Takes up a portion of the screen height
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Welcome to\nDeepPlan",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Bottom Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .padding(horizontal = 24.dp)
            ) {
                // "Your projects" header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your projects",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (showProjectsCard && uiState.finishedLoadingProjects) {
                        IconButton(onClick = { isEditMode = !isEditMode }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = if (isEditMode) MaterialTheme.colorScheme.error
                                else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }

                when {
                    !(uiState.finishedLoadingProjects) -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    showProjectsCard && uiState.finishedLoadingProjects -> {
                        Spacer(modifier = Modifier.height(16.dp))

                        val projects = uiState.projects
                        Log.d("Loading Projects", projects.toString())

                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Project list (or empty state)
                            if (uiState.projects.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(uiState.projects) { project ->
                                        ProjectCard(
                                            project = project,
                                            projectDashboardViewModel = projectDashboardViewModel,
                                            navController = navController,   // ✅ pass the real one
                                            isEditMode = isEditMode,
                                            onDeleteClick = {
                                                projectToDelete = project
                                                showDeleteDialog = true
                                            }
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = "No projects yet",
                                    modifier = Modifier.align(Alignment.Center),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }

                            // ✅ Button is now "anchored" at the bottom right
                            Button(
                                onClick = { navController.navigate(Screen.NewProjectGeneralInformation.name) },
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.addplusicon),
                                        contentDescription = "Add",
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Add New Project",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }

                        if (showDeleteDialog && projectToDelete != null) {
                            AlertDialog(
                                onDismissRequest = { showDeleteDialog = false },
                                title = { Text("Delete Project") },
                                text = { Text("Are you sure you want to delete \"${projectToDelete?.name}\"?") },
                                confirmButton = {
                                    TextButton(onClick = {
                                        // ✅ Pass the whole ProjectOverview object
                                        projectToDelete?.let { project ->
                                            viewModel.deleteProject(project)
                                        }

                                        showDeleteDialog = false
                                        projectToDelete = null
                                    }) {
                                        Text("Delete")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        showDeleteDialog = false
                                        projectToDelete = null
                                    }) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectCard(
    project: ProjectOverview,
    projectDashboardViewModel: ProjectDashboardViewModel,
    navController: NavHostController = rememberNavController(),
    isEditMode: Boolean = false,
    onDeleteClick: () -> Unit = {}

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        onClick = {
            projectDashboardViewModel.setProjectId(project.id)
            navController.navigate(Screen.Home.name)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(12.dp))
                .padding(start = 12.dp, end = 16.dp, top = 8.dp, bottom = 8.dp) // added end = 16.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left: Progress circle with %
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(45.dp)
                ) {
                    CircularProgressIndicator(
                        progress = project.progress,
                        modifier = Modifier.size(45.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 6.dp
                    )
                    Text(
                        text = "${(project.progress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                // Middle: Project name
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.primary)
                )

                Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                    if (isEditMode) {
                        IconButton(onClick = onDeleteClick, modifier = Modifier.size(24.dp)) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Project",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    ManageProjectScreen(
        viewModel = viewModel(),
        projectViewModel = viewModel(),
        projectDashboardViewModel = viewModel(),
    )
}