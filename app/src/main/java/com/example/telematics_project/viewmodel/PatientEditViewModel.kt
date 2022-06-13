package com.example.telematics_project.viewmodel

import androidx.lifecycle.ViewModel
import com.example.telematics_project.model.Patient
import com.example.telematics_project.repository.PatientRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class PatientEditViewModel : ViewModel(), KoinComponent {
    val patientRepository: PatientRepository by inject()

    var pickedSexValue: String? = null


    fun onPickAnswerFemale() {
        pickedSexValue = "Female"
    }

    fun onPickAnswerMale() {
        pickedSexValue = "Male"
    }

    fun onPickAnswerOther() {
        pickedSexValue = "Other"
    }

    fun updatePatientRecord(
        patientId: String,
        name: String,
        age: String,
        sex: String,
        symptoms: String,
        conditions: String,
        add_info: String,
        imagePath: String,
        vector: List<Float>
    ) {
            patientRepository.updatePatientData(
                patientId,
                name,
                age,
                pickedSexValue?: sex,
                symptoms,
                conditions,
                add_info,
                imagePath,
                vector
            )
    }

    fun removePatient(patient: Patient) {
        patientRepository.deletePatient(patient.id)
    }
}