package com.example.MovilesSUax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.MovilesSUax.Screen.ForgotScreen
import com.example.MovilesSUax.screens.LoginScreen
import com.example.MovilesSUax.screens.RegisterScreen
import com.example.MovilesSUax.ui.theme.MyApplicationTheme


class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("main") { MainScreen(navController) }
                    composable("forgot") { ForgotScreen(navController) }
                }
            }
        }
    }
}
