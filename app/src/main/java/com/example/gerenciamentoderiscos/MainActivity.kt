package com.example.gerenciamentoderiscos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.gerenciamentoderiscos.theme.Theme
import com.example.gerenciamentoderiscos.ui.navigation.Navigation
import com.example.gerenciamentoderiscos.viewmodel.RiskMonitorViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: RiskMonitorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Theme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}
