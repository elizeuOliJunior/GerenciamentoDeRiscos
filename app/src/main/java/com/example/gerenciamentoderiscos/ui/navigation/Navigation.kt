package com.example.gerenciamentoderiscos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gerenciamentoderiscos.ui.screens.HomeScreen
import com.example.gerenciamentoderiscos.ui.screens.AllRisksScreen
import com.example.gerenciamentoderiscos.ui.screens.RiskMapScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(navController = navController)
        }
        composable("AllRisksScreen") {
            AllRisksScreen(navController = navController)
        }
        composable("RiskMapScreen") {
            RiskMapScreen()
        }

    }
}