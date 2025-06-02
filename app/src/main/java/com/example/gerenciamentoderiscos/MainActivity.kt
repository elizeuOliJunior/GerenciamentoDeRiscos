package com.example.gerenciamentoderiscos

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.gerenciamentoderiscos.theme.GerenciamentoDeRiscosTheme
import com.example.gerenciamentoderiscos.ui.navigation.AppNavigation
import com.example.gerenciamentoderiscos.viewmodel.RiskMonitorViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: RiskMonitorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GerenciamentoDeRiscosTheme {
                val navController = rememberNavController()

                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(navController = navController)
                }
            }
        }
    }
}
