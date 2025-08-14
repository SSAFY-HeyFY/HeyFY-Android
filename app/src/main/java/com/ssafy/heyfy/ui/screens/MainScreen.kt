package com.ssafy.heyfy.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssafy.heyfy.HomeScreen
import com.ssafy.heyfy.IdScreen
import com.ssafy.heyfy.FinanceScreen
import com.ssafy.heyfy.HomeDetailScreen

sealed class Screen(
    val route: String, 
    val title: String, 
    val icon: ImageVector,
    val contentDescription: String
) {
    object Home : Screen("home", "홈", Icons.Default.Home, "홈 화면")
    object ID : Screen("id", "ID", Icons.Default.AccountCircle, "ID 관리")
    object Finance : Screen("finance", "금융", Icons.Default.FavoriteBorder, "금융 서비스")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(Screen.Home, Screen.ID, Screen.Finance).forEach { screen ->
                    NavigationBarItem(
                        icon = { 
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.contentDescription
                            )
                        },
                        label = { Text(screen.title) },
                        selected = selectedScreen == screen,
                        onClick = {
                            selectedScreen = screen
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToHomeDetail = {
                        navController.navigate("home_detail")
                    }
                )
            }
            composable(Screen.ID.route) {
                IdScreen()
            }
            composable(Screen.Finance.route) {
                FinanceScreen()
            }
            composable("home_detail") {
                HomeDetailScreen()
            }
        }
    }
}
