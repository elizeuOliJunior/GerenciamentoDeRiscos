package com.example.gerenciamentoderiscos.data.model

data class Risk(
    val id: String = "",
    val userId: String = "",
    val riskType: String = "",
    val address: String = "",
    val date: String = "",
    val description: String = "",
    val imageUrl: String? = null,
    val status: String = ""
)
