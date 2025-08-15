package com.example.deepplan.ui.screen.login

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
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
import com.example.deepplan.ui.theme.DeepPlanTheme
import kotlin.math.round
import com.example.deepplan.R
import com.example.deepplan.ui.screen.home.CheckList

@Composable
fun Login(navController: NavHostController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Authenticated -> navController.navigate(Screen.Home.name) {
                popUpTo(Screen.Login.name) { inclusive = true }
            }
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    val checkedState = remember { mutableStateOf(true) }

    Box (
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceTint),
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
                textAlign = TextAlign.Center,
                lineHeight = 39.sp
            )
        }

        Image(
            painter = painterResource(R.drawable.shape),
            contentDescription = "Shape",
            modifier = Modifier.padding(top = 26.dp)
        )

        Image(
            painter = painterResource(R.drawable.shape_1),
            contentDescription = "Shape1",
            modifier = Modifier.padding(top = 217.dp)
        )

        Image(
            painter = painterResource(R.drawable.shape_2),
            contentDescription = "Shape2",
            modifier = Modifier.padding(top = 186.dp).align(Alignment.TopEnd),
        )

        Image(
            painter = painterResource(R.drawable.shape_3),
            contentDescription = "Shape3",
            modifier = Modifier.align(Alignment.TopEnd)
        )

        Surface (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.623f)
                .align(Alignment.BottomCenter)
                .padding(start = 13.dp),
            shape = RoundedCornerShape(topStart = 100.dp),
            color = Color.White,
            tonalElevation = 4.dp
        ) {
            Column (
                modifier = Modifier.padding(start = 38.dp, top = 53.dp, end = 24.dp)
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
                    isError = emailError.isNotEmpty(),
                    label = {
                        if (emailError.isNotEmpty()) {
                            Text(emailError, color = Color.Red, fontSize = 11.sp)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(top = 5.dp),
                    shape = RoundedCornerShape(15.dp)
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
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
                            text = "Enter your password here",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = if(passwordError.isNotEmpty()) Color.Red else Color(0xFFCCCCCC))
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(top = 5.dp),
                    shape = RoundedCornerShape(15.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button (
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        emailError = if(email.isBlank()) "Email is required!" else ""
                        passwordError = if(password.isBlank()) "Password is required!" else ""
                        if(emailError.isEmpty() && passwordError.isEmpty()) {
                            authViewModel.login(email, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "Sign In",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton (
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Sign In with Google",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Checkbox(
                                checked = checkedState.value,
                                onCheckedChange = { checkedState.value = it },
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(9.dp))

                        Text(
                            text = "Remember me",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Text(
                        text = "Forgot password?",
                        color = MaterialTheme.colorScheme.surfaceTint,
                        style = TextStyle(
                            fontSize = 11.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(.1f))

                TextButton(
                    onClick = { navController.navigate(Screen.Register.name) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Don't have an account? Sign Up",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}