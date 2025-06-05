package com.example.gerenciamentoderiscos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gerenciamentoderiscos.data.model.Risk
import com.example.gerenciamentoderiscos.viewmodel.RiskManagerViewModel
import com.example.gerenciamentoderiscos.viewmodel.updateRisk
import java.text.Normalizer

@Composable
fun AllRisksScreen(navController: NavController) {
    val viewModel: RiskManagerViewModel = viewModel()
    val risks = viewModel.allRisks
    val loading = viewModel.isLoading

    var selectedStatus by remember { mutableStateOf("Todos") }
    val statusOptions = listOf("Todos", "Analise", "Aceito", "Recusado")

    var selectedRiskType by remember { mutableStateOf("Todos") }
    val riskTypeOptions = listOf("Todos", "Biológico", "Químico", "Ergonômico", "Físico", "Mecânico")

    val filteredRisks = risks.filter { risk ->
        (selectedStatus == "Todos" || normalize(risk.status) == normalize(selectedStatus)) &&
                (selectedRiskType == "Todos" || normalize(risk.riskType) == normalize(selectedRiskType))
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

            Spacer(modifier = Modifier.height(8.dp))

            RiskTypeDropdown(
                selectedType = selectedRiskType,
                options = riskTypeOptions,
                onTypeSelected = { newType -> selectedRiskType = newType }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(filteredRisks) { risk ->
                    RiskCard(risk = risk)
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

    Column {
        Text("Filtrar por status:",  color = Color.White)
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
}

@Composable
fun RiskTypeDropdown(
    selectedType: String,
    options: List<String>,
    onTypeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Filtrar por tipo de risco:", color = Color.White)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedType)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type) },
                        onClick = {
                            onTypeSelected(type)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RiskCard(risk: Risk) {
    var expanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(risk.status) }
    val statusOptions = listOf("Analise", "Aceito", "Recusado")

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
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

            Text("Status atual:")

            Box {
                OutlinedButton(onClick = { expanded = true }) {
                    Text(selectedStatus)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    statusOptions.forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status) },
                            onClick = {
                                selectedStatus = status
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Tipo: ${risk.riskType}", style = MaterialTheme.typography.bodyMedium)
            Text("Data: ${risk.date}", style = MaterialTheme.typography.bodyMedium)
            Text("Endereço: ${risk.address}", style = MaterialTheme.typography.bodyMedium)
            Text("Descrição: ${risk.description}", style = MaterialTheme.typography.bodyMedium)

            Button(
                onClick = {
                    updateRisk(risk.id, selectedStatus)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Salvar")
            }
        }
    }
}

fun normalize(text: String): String {
    return Normalizer.normalize(text, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
        .lowercase()
}
