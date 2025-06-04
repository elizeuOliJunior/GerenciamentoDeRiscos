package com.example.gerenciamentoderiscos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gerenciamentoderiscos.ui.screens.AllRisksScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "AllRisksScreen") {
        composable("AllRisksScreen") {
            AllRisksScreen()
        }
    }
}
