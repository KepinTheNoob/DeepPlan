package com.example.deepplan.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.deepplan.AuthState
import com.example.deepplan.AuthViewModel
import com.example.deepplan.ui.screen.Screen

@Composable
fun Login(navController: NavHostController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value) {
//            is AuthState.Authenticated -> navController.navigate(Screen.Home.name) {
//                popUpTo(Screen.Login.name) { inclusive = true }
//            }
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Box (
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.377f)
                .background(color = MaterialTheme.colorScheme.surfaceTint),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Welcome to\nDeepPlan",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primaryContainer,
                textAlign = TextAlign.Center
            )
        }

        Surface (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.623f)
                .align(Alignment.BottomCenter)
                .padding(start = 35.dp, end = 35.dp, top = 16.dp, bottom = 31.dp),
        ) {
            Column (
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Username/email",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.surfaceTint,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            text = "examplemail@gmail.com",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = Color(0xFFCCCCCC)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(top = 5.dp)
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Password",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.surfaceTint,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = {
                        Text(
                            text = "Enter your username here",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = Color(0xFFCCCCCC)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(top = 5.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button (
                    shape = RoundedCornerShape(10.dp),
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Sign In")
                }
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton (
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Sign Up with Google")
                }
            }
        }
    }
}