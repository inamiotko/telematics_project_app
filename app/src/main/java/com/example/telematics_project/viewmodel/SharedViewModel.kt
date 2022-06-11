package com.example.telematics_project.viewmodel

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.telematics_project.model.Patient
import com.example.telematics_project.repository.PatientRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class SharedViewModel : ViewModel(), KoinComponent {

    private var patientData: Patient? = null
    private var pickedSexValue: String? = null
    private val patientRepository: PatientRepository by inject()

    fun setPatientDetails(patient: Patient) {
        patientData = patient
    }

    fun getPatientDetails() = patientData

    fun onPickAnswerFemale() {
        pickedSexValue= "Female"

    }

    fun onPickAnswerMale() {
        pickedSexValue= "Male"
    }

    fun onPickAnswerOther() {
        pickedSexValue= "Other"
    }

    fun addPatientRecord(
        patientId: String,
        name: String,
        age: String,
        symptoms: String,
        conditions: String,
        add_info: String,
        imagePath: String,
        imageVector: List<Float>
    ) {
        pickedSexValue?.let{
            patientRepository.createPatient(
                patientId,
                name,
                age,
                it,
                symptoms,
                conditions,
                add_info,
                imagePath,
                imageVector
            )
        }
    }
}