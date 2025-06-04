package com.example.gerenciamentoderiscos.data.repository

import com.example.gerenciamentoderiscos.data.model.Risk
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val risksCollection = firestore.collection("risks")

    suspend fun getAllRisks(): List<Risk> {
        return try {
            val snapshot = risksCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(Risk::class.java)?.copy(id = it.id) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}