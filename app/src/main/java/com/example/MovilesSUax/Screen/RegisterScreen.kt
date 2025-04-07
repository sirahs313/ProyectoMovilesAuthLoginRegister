package com.example.MovilesSUax.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.MovilesSUax.RetrofitInstance
import com.example.MovilesSUax.api.ApiResponse
import com.example.MovilesSUax.api.RegisterRequest

import com.example.myapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegisterScreen(navController: NavController) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App logo",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Title - SIGN UP
        Text(
            text = "SIGN UP",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // Name Field
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
            ),
            shape = RoundedCornerShape(0.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
            ),
            shape = RoundedCornerShape(0.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
            ),
            shape = RoundedCornerShape(0.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Register Button
        Button(
            onClick = {
                isLoading.value = true
                val request = RegisterRequest(
                    name = name.value,
                    email = email.value,
                    password = password.value
                )
                RetrofitInstance.apiService.signup(request)
                    .enqueue(object : Callback<ApiResponse> {
                        override fun onResponse(
                            call: Call<ApiResponse>,
                            response: Response<ApiResponse>
                        ) {
                            isLoading.value = false
                            if (response.isSuccessful) {
                                message.value = response.body()?.message ?: "Registration successful"
                                navController.navigate("login") { popUpTo("register") { inclusive = true } }
                            } else {
                                message.value = "Registration error"
                            }
                        }

                        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                            isLoading.value = false
                            message.value = "Error: ${t.message}"
                        }
                    })
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = !isLoading.value,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Text("Register")
            }
        }

        // Message
        if (message.value.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message.value,
                color = if (message.value.contains("success", ignoreCase = true))
                    Color(0xFF4CAF50) // Green for success
                else
                    Color(0xFFF44336) // Red for errors
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Login link at bottom
        Row(
            modifier = Modifier.padding(bottom = 40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account? ", color = Color.Gray)
            Text(
                text = "SIGN IN",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate("login") }
            )
        }
    }
}