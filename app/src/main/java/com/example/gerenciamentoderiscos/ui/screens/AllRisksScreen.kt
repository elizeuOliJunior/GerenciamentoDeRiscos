package com.example.gerenciamentoderiscos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gerenciamentoderiscos.data.model.Risk
import com.example.gerenciamentoderiscos.viewmodel.RiskManagerViewModel
import java.text.Normalizer

@Composable
fun AllRisksScreen(navController: NavController) {
    val viewModel: RiskManagerViewModel = viewModel()
    val risks = viewModel.allRisks
    val loading = viewModel.isLoading

    var selectedStatus by remember { mutableStateOf("Todos") }
    val statusOptions = listOf("Todos", "Analise", "Aceito", "Recusado")

    // Filtro com normalização (remove acentos, ignora caixa)
    val filteredRisks = if (selectedStatus == "Todos") {
        risks
    } else {
        risks.filter {
            normalize(it.status) == normalize(selectedStatus)
        }
    }

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            StatusDropdown(
                selectedStatus = selectedStatus,
                options = statusOptions,
                onStatusSelected = { newStatus -> selectedStatus = newStatus }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(filteredRisks) { risk ->
                    RiskCard(risk)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun StatusDropdown(
    selectedStatus: String,
    options: List<String>,
    onStatusSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedStatus)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { status ->
                DropdownMenuItem(
                    text = { Text(status) },
                    onClick = {
                        onStatusSelected(status)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun RiskCard(risk: Risk) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            risk.imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text("Status: ${risk.status}", style = MaterialTheme.typography.titleMedium)
            Text("Tipo: ${risk.riskType}", style = MaterialTheme.typography.bodyMedium)
            Text("Data: ${risk.date}", style = MaterialTheme.typography.bodyMedium)
            Text("Endereço: ${risk.address}", style = MaterialTheme.typography.bodyMedium)
            Text("Descrição: ${risk.description}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// Função para remover acentos e padronizar letras
fun normalize(text: String): String {
    return Normalizer.normalize(text, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
        .lowercase()
}
