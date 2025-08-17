package com.example.deepplan

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.deepplan.data.Screen
import com.example.deepplan.ui.screen.home.Home
import com.example.deepplan.ui.screen.login.Login
import com.example.deepplan.ui.screen.manageProject.ManageProjectScreen
import com.example.deepplan.ui.screen.manageProject.ManageProjectViewModel
import com.example.deepplan.ui.screen.newProject.ExternalContextScreen
import com.example.deepplan.ui.screen.newProject.GeneralInformationScreen
import com.example.deepplan.ui.screen.newProject.InternalFactorsScreen
import com.example.deepplan.ui.screen.newProject.NewProjectViewModel
import com.example.deepplan.ui.screen.newProject.PredictionResultsContent
import com.example.deepplan.ui.screen.newProject.PredictionResultsScreen
import com.example.deepplan.ui.screen.newProject.TechnicalScopeScreen
import com.example.deepplan.ui.screen.profile.ProfileScreen
import com.example.deepplan.ui.screen.register.Register

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenBar(
    currentScreen: Screen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navController: NavController,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("")
        },

        navigationIcon = {
            if (currentScreen in  listOf<Screen>(Screen.Home)) {
                // Taro App Bar Home disini
            } else if (currentScreen in listOf<Screen>(
                    Screen.NewProjectGeneralInformation,
                    Screen.NewProjectTechnicalScope,
                    Screen.NewProjectExternalContext,
                    Screen.NewProjectInternalFactors
            )) {
                IconButton (
                    onClick = {},
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            } else if (currentScreen in listOf<Screen>(
                    Screen.ManageProject
            )) {
                IconButton (
                    onClick = {},
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Back",
                    )
                }
            }
        }
    )
}

@Composable
fun MainContent(
    startScreen: Screen,
    navController: NavHostController,
    innerPadding: PaddingValues,
    newProjectViewModel: NewProjectViewModel,
    authViewModel: AuthViewModel = AuthViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = startScreen.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.name) {
            Home()
        }

        composable(Screen.NewProjectGeneralInformation.name) {
            GeneralInformationScreen(
                viewModel = newProjectViewModel,
                navController = navController,
            )
        }

        composable(Screen.NewProjectTechnicalScope.name) {
            TechnicalScopeScreen(
                viewModel = newProjectViewModel,
                navController = navController,
            )
        }

        composable(Screen.NewProjectExternalContext.name) {
            ExternalContextScreen(
                viewModel = newProjectViewModel,
                navController = navController,
            )
        }

        composable(Screen.NewProjectInternalFactors.name) {
            InternalFactorsScreen(
                viewModel = newProjectViewModel,
                navController = navController,
            )
        }

        composable(Screen.Prediction.name) {
            PredictionResultsScreen(
                viewModel = newProjectViewModel,
                navController = navController
            )
        }

        composable(Screen.ManageProject.name) {
            ManageProjectScreen(
                viewModel = viewModel(),
                navController = navController
            )
        }

        composable(Screen.Login.name) {
            Login(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.Register.name) {
            Register(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.Profile.name) {
            ProfileScreen(
                navController = navController
            )
        }
    }
}

@Composable
fun MainScreen(
    newProjectViewModel: NewProjectViewModel = viewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.Home.name
    )
    var startScreen by remember { mutableStateOf<Screen>(Screen.Profile) }

    when (currentScreen) {
        Screen.Home -> {
            Scaffold(
                topBar = {
                    MainScreenBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        navController = navController,
                    )
                }
            ) { innerPadding ->
                MainContent(
                    startScreen = startScreen,
                    navController = navController,
                    innerPadding = innerPadding,
                    newProjectViewModel = newProjectViewModel,
                )
            }
        }

        else -> {
            Scaffold(
                topBar = {
                    MainScreenBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        navController = navController,
                    )
                }
            ) { innerPadding ->
                MainContent(
                    startScreen = startScreen,
                    navController = navController,
                    innerPadding = innerPadding,
                    newProjectViewModel = newProjectViewModel,
                )
            }
        }

    }
}