package com.example.gerenciamentoderiscos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gerenciamentoderiscos.data.model.Risk
import com.example.gerenciamentoderiscos.viewmodel.RiskManagerViewModel

@Composable
fun AllRisksScreen(viewModel: RiskManagerViewModel = RiskManagerViewModel()) {
    val risks = viewModel.allRisks
    val loading = viewModel.isLoading

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(risks) { risk ->
                RiskCard(risk)
                Spacer(modifier = Modifier.height(16.dp))
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
