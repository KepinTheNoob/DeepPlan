package com.example.deepplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deepplan.ui.screen.Screen
import com.example.deepplan.ui.screen.home.Home
import com.example.deepplan.ui.screen.login.Login
import com.example.deepplan.ui.screen.register.Register
import com.example.deepplan.ui.theme.DeepPlanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeepPlanTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent(
    authViewModel: AuthViewModel = AuthViewModel(),
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Register.name
    ) {
        composable(Screen.Home.name) {
            Home()
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
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    DeepPlanTheme {
//        MainContent()
//    }
//}