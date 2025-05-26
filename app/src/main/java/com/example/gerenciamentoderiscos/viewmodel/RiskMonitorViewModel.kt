package com.example.gerenciamentoderiscos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gerenciamentoderiscos.data.model.Risk
import com.example.gerenciamentoderiscos.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RiskMonitorViewModel : ViewModel() {

    private val repository = FirebaseRepository()

    private val _risks = MutableStateFlow<List<Risk>>(emptyList())
    val risks: StateFlow<List<Risk>> = _risks

    fun loadRisksByType(type: String) {
        viewModelScope.launch {
            val fetchedRisks = repository.getRisksByType(type)
            _risks.value = fetchedRisks
        }
    }
}
