package com.example.deepplan.ui.screen.manageProject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.deepplan.data.Screen

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
    navController: NavController = rememberNavController(),
) {
    val uiState by viewModel.uiState.collectAsState()

    var showProjectsCard by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.finishedLoadingProjects) {
        viewModel.loadProjects()
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
//                // Top app bar with icons
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Menu,
//                        contentDescription = "Menu",
//                        tint = MaterialTheme.colorScheme.onPrimary
//                    )
////                    Image(
////                        painter = painterResource(id = com.example.deepplan.R.drawable.avatar), // Use your image name here
////                        contentDescription = "User Profile",
////                        modifier = Modifier.size(24.dp), // Set the desired size for the image
////                    )
//                }

                Text(
                    text = "Welcome to\nDeepPlan",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

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
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                when {
                    !(uiState.finishedLoadingProjects) -> {
                        CircularProgressIndicator()
                    }
                    showProjectsCard && uiState.finishedLoadingProjects -> {

                        Spacer(modifier = Modifier.height(16.dp))

                        // List of project cards
                        val projects = uiState.projects
                        Log.d("Loading Projects", projects.toString())

                        if (projects.isNotEmpty()) {
                            projects.forEach { project ->
                                ProjectCard(project)
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                        else {
                            Text(
                                text = "No projects found",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }


                        Spacer(modifier = Modifier.weight(1f)) // Push the button to the bottom

                        // "Add New Project" button
                        Button(
                            onClick = {
                                navController.navigate(Screen.NewProjectGeneralInformation.name)
                            },
                            modifier = Modifier
                                .align(Alignment.End) // Posisikan di kanan bawah
                                .padding(bottom = 16.dp), // Tambahkan padding untuk jarak dari tepi
                            shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Add New Project", color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ProjectCard(project: Project) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        onClick = {}
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
                    text = project.projectName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.width(12.dp))
                // Right: Grid icon
                Image(
                    painter = painterResource(R.drawable.grid),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    ManageProjectScreen(
        viewModel = viewModel(),
    )
}