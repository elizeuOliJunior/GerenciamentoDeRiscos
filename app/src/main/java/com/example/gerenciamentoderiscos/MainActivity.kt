package com.example.gerenciamentoderiscos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gerenciamentoderiscos.ui.navigation.AppNavigation
import com.example.gerenciamentoderiscos.ui.theme.RegistroDeRiscosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroDeRiscosTheme {
                AppNavigation()
            }
        }
    }
}