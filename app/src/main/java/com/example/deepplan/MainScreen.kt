package com.example.deepplan

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
import com.example.deepplan.ui.screen.newProject.PredictionResultsScreen
import com.example.deepplan.ui.screen.newProject.TechnicalScopeScreen
import com.example.deepplan.ui.screen.profile.ProfileScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenBar(
    currentScreen: Screen,
    navController: NavController,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onMenuClicked: () -> Unit,
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
            Log.d("Current Screen", currentScreen.toString())
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
                    Screen.ManageProject,
                    Screen.Profile
            )) {
                IconButton (
                    onClick = onMenuClicked,
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
    authViewModel: AuthViewModel,
    manageProjectViewModel: ManageProjectViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startScreen.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Login.name) {
            Login(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.Home.name) {
            Home()
        }

        composable(Screen.Profile.name) {
            ProfileScreen()
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
                viewModel = manageProjectViewModel,
                navController = navController
            )
        }


    }
}

@Composable
fun MainScreen(
    context: Context,
    newProjectViewModel: NewProjectViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    manageProjectViewModel: ManageProjectViewModel = viewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.ManageProject.name
    )
    var startScreen by remember { mutableStateOf<Screen>(Screen.ManageProject) }

    // Authentification
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        Log.d("Auth", authState.value.toString())
        when(authState.value) {
            is AuthState.Authenticated -> startScreen = Screen.ManageProject
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> startScreen = Screen.Login
        }
    }

    // Drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerScope = rememberCoroutineScope()

    when (currentScreen) {
        Screen.Home -> {
            Scaffold(
                topBar = {
                    MainScreenBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        navController = navController,
                        onMenuClicked = { /*TODO*/ },
                    )
                }
            ) { innerPadding ->
                MainContent(
                    startScreen = startScreen,
                    navController = navController,
                    innerPadding = innerPadding,
                    newProjectViewModel = newProjectViewModel,
                    authViewModel = authViewModel,
                    manageProjectViewModel = manageProjectViewModel,
                )
            }
        }

        Screen.ManageProject, Screen.Profile -> {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    Box(
                        modifier = Modifier
                            .padding(end = 50.dp)
                    ) {
                        ModalDrawerSheet {
                            Text("DeepPlan")
                            HorizontalDivider()
                            NavigationDrawerItem(
                                label = { Text("Projects List") },
                                selected = if (currentScreen == Screen.ManageProject) true else false,
                                onClick = {
                                    if (currentScreen != Screen.ManageProject) {
                                        navController.navigate(Screen.ManageProject.name)
                                    }
                                    drawerScope.launch {
                                        drawerState.apply {
                                            if (isOpen) close() else open()
                                        }
                                    }
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text("Profile") },
                                selected = if (currentScreen == Screen.Profile) true else false,
                                onClick = {
                                    if (currentScreen != Screen.Profile) {
                                        navController.navigate(Screen.Profile.name)
                                    }
                                    drawerScope.launch {
                                        drawerState.apply {
                                            if (isOpen) close() else open()
                                        }
                                    }
                                }
                            )
                        }
                    }
                },
                gesturesEnabled = true,
            ) {
                Scaffold(
                    topBar = {
                        MainScreenBar(
                            currentScreen = currentScreen,
                            canNavigateBack = navController.previousBackStackEntry != null,
                            navigateUp = { navController.navigateUp() },
                            navController = navController,
                            onMenuClicked = {
                                drawerScope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    MainContent(
                        startScreen = startScreen,
                        navController = navController,
                        innerPadding = innerPadding,
                        newProjectViewModel = newProjectViewModel,
                        authViewModel = authViewModel,
                        manageProjectViewModel = manageProjectViewModel,
                    )
                }
            }
        }

        Screen.NewProjectGeneralInformation,
        Screen.NewProjectTechnicalScope,
        Screen.NewProjectExternalContext,
        Screen.NewProjectInternalFactors -> {
            Scaffold(
                topBar = {
                    MainScreenBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        navController = navController,
                        onMenuClicked = {}
                    )
                }
            ) { innerPadding ->
                MainContent(
                    startScreen = startScreen,
                    navController = navController,
                    innerPadding = innerPadding,
                    newProjectViewModel = newProjectViewModel,
                    authViewModel = authViewModel,
                    manageProjectViewModel = manageProjectViewModel,
                )
            }
        }
        else -> {
            Scaffold() { innerPadding ->
                MainContent(
                    startScreen = startScreen,
                    navController = navController,
                    innerPadding = innerPadding,
                    newProjectViewModel = newProjectViewModel,
                    authViewModel = authViewModel,
                    manageProjectViewModel = manageProjectViewModel,
                )
            }
        }

    }
}