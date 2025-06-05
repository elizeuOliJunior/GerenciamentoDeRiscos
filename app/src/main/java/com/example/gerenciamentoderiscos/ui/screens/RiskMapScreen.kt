package com.example.gerenciamentoderiscos.ui.screens

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class RiskLocation(val title: String, val address: String)

@Composable
fun RiskMapScreen() {
    val context = LocalContext.current
    var riskLocations by remember { mutableStateOf<List<LatLngMarker>>(emptyList()) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-22.9, -47.05), 12f) // Campinas
    }

    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        val risks = db.collection("risks").get().await()

        val geocoder = android.location.Geocoder(context)
        val locations = mutableListOf<LatLngMarker>()

        for (document in risks.documents) {
            val address = document.getString("address") ?: continue
            val title = document.getString("description") ?: "Sem título"
            try {
                val results = geocoder.getFromLocationName(address, 1)
                if (!results.isNullOrEmpty()) {
                    val loc = results[0]
                    locations.add(
                        LatLngMarker(title, LatLng(loc.latitude, loc.longitude))
                    )
                }
            } catch (e: Exception) {
                Log.e("Geocoding", "Erro ao converter endereço: $address", e)
            }
        }

        riskLocations = locations
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            riskLocations.forEach { marker ->
                Marker(
                    state = MarkerState(position = marker.latLng),
                    title = marker.title
                )
            }
        }
    }
}

data class LatLngMarker(val title: String, val latLng: LatLng)
