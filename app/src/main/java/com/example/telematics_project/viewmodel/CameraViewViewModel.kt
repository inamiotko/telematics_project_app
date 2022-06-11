package com.example.telematics_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.telematics_project.model.Patient
import com.example.telematics_project.repository.PatientRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.math.pow
import kotlin.math.sqrt

class CameraViewViewModel : ViewModel(), KoinComponent {
    val patients: MutableLiveData<List<Patient>> = MutableLiveData(mutableListOf())
    val patientRepo: PatientRepository by inject()


    fun start() {
        patientRepo.getPatients(patients)
    }

    fun cosineSimilarity(vectorA: List<Float>, vectorB: List<Float>): Double {
        var dotProduct = 0.0
        var normA = 0.0
        var normB = 0.0
        for (i in vectorA.indices) {
            dotProduct += vectorA[i] * vectorB[i]
            normA += vectorA[i].toDouble().pow(2.0)
            normB += vectorB[i].toDouble().pow(2.0)
        }
        return dotProduct / (sqrt(normA) * sqrt(normB))
    }
}