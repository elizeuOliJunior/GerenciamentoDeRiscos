package com.example.gerenciamentoderiscos.data.repository

import android.net.Uri
import com.example.gerenciamentoderiscos.data.model.Risk
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*

class FirebaseRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val risksCollection = firestore.collection("risks")

    // Upload de imagem (opcional)
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference.child("risk_images")

    suspend fun uploadImageToStorage(uri: Uri): String {
        val fileName = "risk_image_${UUID.randomUUID()}.png"
        val imageRef = storageRef.child(fileName)

        imageRef.putFile(uri).await()
        return imageRef.downloadUrl.await().toString()

    }

    // Adiciona risco ao Firestore
    suspend fun addRisk(risk: Risk): Result<Unit> {
        return try {
            val riskMap = hashMapOf(
                "description" to risk.description,
                "address" to risk.address,
                "riskType" to risk.riskType,
                "imageUrl" to risk.imageUrl,
                "userId" to risk.userId
            )
            risksCollection.add(riskMap).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // (Futuramente) buscar riscos
    suspend fun getRisks(): List<Risk> {
        return try {
            val snapshot = risksCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(Risk::class.java)?.copy(id = it.id) }
        } catch (e: Exception) {
            emptyList()
        }
    }



}