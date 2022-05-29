package com.example.telematics_project.viewmodel

import androidx.lifecycle.ViewModel
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
        symptoms: String,
        conditions: String,
        add_info: String,
        imagePath: String
    ) {
        pickedSexValue?.let{
            patientRepository.updatePatientData(
                patientId,
                name,
                age,
                it,
                symptoms,
                conditions,
                add_info,
                imagePath
            )
        }
    }
}