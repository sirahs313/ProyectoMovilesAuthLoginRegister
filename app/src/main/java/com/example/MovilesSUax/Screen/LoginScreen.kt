package com.example.MovilesSUax.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.MovilesSUax.RetrofitInstance
import com.example.MovilesSUax.api.ApiResponse
import com.example.MovilesSUax.api.LoginRequest
import com.example.myapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }
    val showFingerprintButton = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }

    // Check if user has logged in before (has saved credentials)
    LaunchedEffect(Unit) {
        val savedEmail = sharedPreferences.getString("saved_email", null)
        if (!savedEmail.isNullOrEmpty()) {
            email.value = savedEmail
            showFingerprintButton.value = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Espacio para el logo (reemplaza con tu imagen)
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            painter = painterResource(id = R.drawable.logo), // Asume que tu archivo se llama logo.jpg
            contentDescription = "Logo de la aplicación", // Texto accesible para lectores de pantalla
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit // Ajusta la escala de la imagen
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Title - SIGN IN
        Text(
            text = "SIGN IN",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 40.dp)
        )


        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,  // Color del texto cuando está enfocado
                unfocusedTextColor = Color.Black, // Color del texto cuando no está enfocado
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
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,  // Color del texto cuando está enfocado
                unfocusedTextColor = Color.Black, // Color del texto cuando no está enfocado
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
            ),
            shape = RoundedCornerShape(0.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password
        Text(
            text = "Forgot Password",
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { navController.navigate("forgot") }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        Button(
            onClick = {
                isLoading.value = true
                performLogin(
                    email.value,
                    password.value,
                    navController,
                    { msg ->
                        message.value = msg
                        isLoading.value = false
                    },
                    sharedPreferences,
                    { show ->
                        showFingerprintButton.value = show
                        isLoading.value = false
                    }
                )
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
                Text("Continue")
            }
        }

        // Fingerprint Button (opcional, se muestra si hay credenciales guardadas)
        if (showFingerprintButton.value) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    authenticateWithFingerprint(
                        context = context,
                        onSuccess = {
                            message.value = "Autenticación exitosa"
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onError = { error ->
                            message.value = "Error: $error"
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Huella digital",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Usar Huella Digital")
            }
        }

        // Mensajes de estado
        if (message.value.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message.value,
                color = if (message.value.contains("éxito", ignoreCase = true))
                    Color(0xFF4CAF50) // Verde para éxito
                else
                    Color(0xFFF44336) // Rojo para errores
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Sign up section at bottom
        Row(
            modifier = Modifier.padding(bottom = 40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an account? ", color = Color.Gray)
            Text(
                text = "SIGN UP",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate("register") }
            )
        }
    }
}

// FUNCIONES ORIGINALES (MANTENIDAS SIN CAMBIOS)
private fun performLogin(
    email: String,
    password: String,
    navController: NavController,
    onMessage: (String) -> Unit,
    sharedPreferences: SharedPreferences,
    onShowFingerprint: (Boolean) -> Unit
) {
    val loginRequest = LoginRequest(email, password)
    RetrofitInstance.apiService.login(loginRequest).enqueue(object : Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                onMessage(response.body()?.message ?: "Login Success!")
                sharedPreferences.edit()
                    .putString("saved_email", email)
                    .apply()
                onShowFingerprint(true)

                navController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                onMessage("Login Failed")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            onMessage("Error: ${t.message}")
        }
    })
}

private fun authenticateWithFingerprint(
    context: Context,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val executor: Executor = ContextCompat.getMainExecutor(context)
    val biometricManager = BiometricManager.from(context)

    when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Fingerprint Login")
                .setSubtitle("Use your fingerprint to login")
                .setNegativeButtonText("Use Account Password")
                .build()

            val biometricPrompt = BiometricPrompt(
                context as FragmentActivity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        onError(errString.toString())
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        onSuccess()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        onError("Authentication failed")
                    }
                })

            biometricPrompt.authenticate(promptInfo)
        }
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> onError("No fingerprint sensor available")
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> onError("Fingerprint sensor is unavailable")
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> onError("No fingerprints enrolled")
        else -> onError("Unknown error")
    }
}