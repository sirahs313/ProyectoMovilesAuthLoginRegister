package com.example.MovilesSUax.Screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ForgotScreen(navController: NavController) {
    val code = remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Text(
                text = "Verification",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter the code sent to your email",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF666666)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo de código
            OutlinedTextField(
                value = code.value,
                onValueChange = { code.value = it },
                label = { Text("Verification Code") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color(0xFF666666),
                    unfocusedLabelColor = Color(0xFF666666),
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de verificar
            Button(
                onClick = {
//                    // Aquí puedes validar el código
//                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE),
                    contentColor = Color.White
                )
            ) {
                Text("Verify")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Volver al login
            TextButton(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF6200EE)
                )
            ) {
                Text("Back to Login")
            }
        }
    }
}
