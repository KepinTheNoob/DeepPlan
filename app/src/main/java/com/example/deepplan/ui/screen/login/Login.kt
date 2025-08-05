package com.example.deepplan.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextField

@Preview(showBackground = true)
@Composable
fun Login() {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }

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
                text = "Welcome to DeepPlan",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }

        Surface (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.623f)
                .align(Alignment.BottomCenter)
                .padding(start = 35.dp, end = 35.dp, top = 16.dp, bottom = 31.dp),
        ) {
            Column {
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
                    label = {
                        Text(
                            text = "examplemail@gmail.com",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = Color(0xFFCCCCCC)
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors()
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun reeeeeee() {
//        Login()
//}