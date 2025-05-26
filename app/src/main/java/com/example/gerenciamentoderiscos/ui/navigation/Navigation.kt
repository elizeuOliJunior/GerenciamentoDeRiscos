package com.example.gerenciamentoderiscos.ui.navigation



import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gerenciamentoderiscos.ui.screens.RiskListScreen
import com.example.gerenciamentoderiscos.ui.screens.SelectRiskTypeScreen

sealed class Screen(val route: String) {
    object SelectType : Screen("select_type")
    object RiskList : Screen("risk_list")
}


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SelectType.route) {
        composable(Screen.SelectType.route) {
            SelectRiskTypeScreen(navController)
        }
        composable(Screen.RiskList.route) {
            RiskListScreen(navController)
        }
    }
}
