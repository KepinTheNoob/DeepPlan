package com.example.deepplan.ui.screen.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.deepplan.AuthState
import com.example.deepplan.AuthViewModel
import com.example.deepplan.R
import com.example.deepplan.data.Screen

@Composable
fun Register(authViewModel: AuthViewModel, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Authenticated -> navController.navigate(Screen.Home.name) {
                popUpTo(Screen.Register.name) { inclusive = true }
            }
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

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
                textAlign = TextAlign.Center
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
                .padding(end = 17.dp),
            shape = RoundedCornerShape(topEnd = 100.dp),
            color = Color.White,
            tonalElevation = 4.dp
        ) {
            Column (
                modifier = Modifier.padding(start = 22.dp, top = 28.dp, end = 26.dp)
            ) {
                Text(
                    text = "Email",
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
                    placeholder = {
                        Text(
                            text = "examplemail@gmail.com",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = if(passwordError.isNotEmpty()) Color.Red else Color(0xFFCCCCCC)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 11.sp),
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Username",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.surfaceTint,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    isError = usernameError.isNotEmpty(),
                    placeholder = {
                        Text(
                            text = "Enter your username here",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = if (usernameError.isNotEmpty()) Color.Red else Color(0xFFCCCCCC)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    shape = RoundedCornerShape(20.dp)
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
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(
                            text = "Enter your password here",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = if(passwordError.isNotEmpty()) Color.Red else Color(0xFFCCCCCC))
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    shape = RoundedCornerShape(20.dp)
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Confirm Password",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.surfaceTint,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    placeholder = {
                        Text(
                            text = "Re-enter your password here",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = if(passwordError.isNotEmpty()) Color.Red else Color(0xFFCCCCCC)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    shape = RoundedCornerShape(20.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button (
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        emailError =
                            if(email.isBlank()) "Email is required!"
                            else if(!email.contains("@")) "Email must contain '@'!"
                            else ""
                        passwordError =
                            if(password.isBlank()) "Password is required!"
                            else if(password.length < 8) "Password must have at least 8 characters!"
                            else ""
                        confirmPasswordError =
                            if(confirmPassword.isBlank()) "Confirm is required!"
                            else if(confirmPassword != password) "Must be same with password!"
                            else ""
                        usernameError =
                            if (username.isBlank()) "Username is required!"
                            else if (username.length < 3) "Username must be at least 3 characters!"
                            else if (!username.matches(Regex("^[A-Za-z0-9_\\.]+$")))
                                "Only letters, numbers, underscores, and dots are allowed!"
                            else ""
                        if(emailError.isEmpty() && passwordError.isEmpty() && confirmPasswordError.isEmpty()) {
                            authViewModel.signup(email, password, username)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primaryContainer,
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
                        text = "Sign Up with Google",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(.1f))

                TextButton(
                    onClick = {
                        navController.navigate(Screen.Login.name)
                    },
                    enabled = authState.value != AuthState.Loading,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Already have an account? Sign In",
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