package com.example.gerenciamentoderiscos.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gerenciamentoderiscos.data.model.Risk
import com.example.gerenciamentoderiscos.data.repository.FirebaseRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class RiskManagerViewModel(
    private val repository: FirebaseRepository = FirebaseRepository()
) : ViewModel() {

    var allRisks by mutableStateOf<List<Risk>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        loadAllRisks()
    }

    private fun loadAllRisks() {
        viewModelScope.launch {
            isLoading = true
            allRisks = repository.getAllRisks()
            isLoading = false
        }
    }
}

fun updateRisk(riskId: String, status: String) {
    val db = FirebaseFirestore.getInstance()
    val docRef = db.collection("risks").document(riskId)

    docRef.update("status", status)
        .addOnSuccessListener {
            //Log.d("Firebase", "Status atualizado com sucesso!")
        }
        .addOnFailureListener { e ->
           // Log.w("Firebase", "Erro ao atualizar status", e)
        }
}
