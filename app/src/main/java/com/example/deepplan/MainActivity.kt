package com.example.deepplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deepplan.data.Screen
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
                MainScreen(this)
            }
        }
    }
}