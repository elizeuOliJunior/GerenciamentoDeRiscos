package com.example.gerenciamentoderiscos.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gerenciamentoderiscos.data.model.Risk
import com.example.gerenciamentoderiscos.data.repository.FirebaseRepository
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
