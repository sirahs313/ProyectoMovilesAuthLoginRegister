package com.example.MovilesSUax


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.MovilesSUax.pages.HomePage
import com.example.MovilesSUax.pages.NotificationPage
import com.example.MovilesSUax.pages.ProfilePage
import com.example.MovilesSUax.pages.SettingsPage
import androidx.navigation.NavController
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.TextButton
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavController, modifier: Modifier = Modifier) {
    val internalNavController = rememberNavController()
    var showMenu by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                NavigationBar(modifier = Modifier.align(Alignment.Center)) {
                    val navItemList = listOf(
                        NavItem("Home", Icons.Default.Home, 0),
                        NavItem("Notification", Icons.Default.Notifications, 5),
                        NavItem("Settings", Icons.Default.Settings, 0),
                        NavItem("Profile", Icons.Default.Person, 0)
                    )

                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = internalNavController.currentBackStackEntryAsState().value?.destination?.route == navItem.label,
                            onClick = { internalNavController.navigate(navItem.label) },
                            icon = {
                                BadgedBox(badge = {
                                    if (navItem.badgeCount > 0) {
                                        Badge {
                                            Text(text = navItem.badgeCount.toString())
                                        }
                                    }
                                }) {
                                    Icon(imageVector = navItem.icon, contentDescription = "Icon")
                                }
                            },
                            label = { Text(navItem.label) }
                        )
                    }
                }

                // Botón flotante centrado con menú mejorado
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-64).dp)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(y = (18 ).dp)
                            .size(40.dp) // Fija el tamaño
                    ) {
                        FloatingActionButton(
                            onClick = { showMenu = !showMenu },
                            containerColor = Color(0xFF9C27B0),
                            elevation = FloatingActionButtonDefaults.elevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 4.dp,
                                hoveredElevation = 4.dp,
                                focusedElevation = 4.dp
                            ),
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        ) {
                            Text("+", fontSize = 26.sp, color = Color.White)
                        }
                    }

                    // Menú desplegable FloatingAction
                    if (showMenu) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .offset(y = (-60).dp)
                                .width(120.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            // Única opción: Cerrar sesión
                            TextButton(
                                onClick = {
                                    showMenu = false
                                    navController.navigate("login") { popUpTo(0) }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    Icons.Default.ExitToApp,
                                    contentDescription = "Cerrar sesión",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text("Cerrar sesión")
                            }
                        }
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        NavHost(
            navController = internalNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomePage() }
            composable("notification") { NotificationPage() }
            composable("profile") { ProfilePage() }
            composable("settings") { SettingsPage() }
        }
    }
}