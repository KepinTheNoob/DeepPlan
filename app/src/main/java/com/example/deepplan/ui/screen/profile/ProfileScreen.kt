package com.example.deepplan.ui.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deepplan.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.deepplan.AuthState
import com.example.deepplan.AuthViewModel
import com.example.deepplan.data.Screen
import com.example.deepplan.ui.theme.DeepPlanTheme
import java.util.concurrent.BlockingDeque
import kotlin.math.round

@Composable
fun ProfileUI(
    username: String,
    email: String,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    authViewModel: AuthViewModel,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .fillMaxHeight(0.377f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.avatar),
                contentDescription = "Avatar",
                modifier = Modifier.size(106.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Jefferson Darren Cendres",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(vertical = 12.dp, horizontal = 30.dp)
                    .clickable {  }
            ) {
                Text(
                    text = "Edit Profile",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        Column (
            modifier = Modifier
                .padding(vertical = 29.dp, horizontal = 33.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Username",
                style = TextStyle(
                    color = Color(0xFF000000),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(7.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { onUsernameChange(it) },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().background(Color(0xFFEFEFEF)),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceTint,
                    focusedBorderColor = MaterialTheme.colorScheme.surfaceTint
                )
            )

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = "Email",
                style = TextStyle(
                    color = Color(0xFF000000),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(7.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { onEmailChange(it) },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().background(Color(0xFFEFEFEF)),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceTint,
                    focusedBorderColor = MaterialTheme.colorScheme.surfaceTint
                )
            )

            Spacer(modifier = Modifier.weight(.1f))

            Button(
                onClick = {
                    authViewModel.signout()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFB3261E)
                ),
                border = BorderStroke(1.dp, Color(0xFFFF0000)),
                elevation = ButtonDefaults.buttonElevation(0.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Sign Out of Account",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(
    navController: NavHostController,
            authViewModel: AuthViewModel = viewModel()
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val userId = auth.currentUser?.uid
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.observeAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            navController.navigate(Screen.Login.name) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    if (authState is AuthState.Authenticated) {
        ProfileUI(
            username = username,
            email = email,
            onUsernameChange = { username = it },
            onEmailChange = { email = it },
            authViewModel = authViewModel,
            onSignOut = {
                authViewModel.signout()
                navController.navigate(Screen.Login.name) {
                    popUpTo(0)
                }
            }
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun reeee() {
//    DeepPlanTheme {
//        Profile(
//            "Jefferson Darren Cendres",
//            "jefferson@example.com",
//            onUsernameChange = {},
//            onEmailChange = {})
//    }
//}
