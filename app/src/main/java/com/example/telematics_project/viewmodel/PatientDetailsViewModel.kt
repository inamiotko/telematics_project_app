package com.example.telematics_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.telematics_project.model.Patient
import com.example.telematics_project.repository.PatientRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class PatientDetailsViewModel : ViewModel(), KoinComponent {
    private val patientRepository: PatientRepository by inject()
    var patientLiveData = MutableLiveData<Patient>()

    fun start(patient: Patient?) {
        patient?.let {
            patientRepository.getPatient(patient.id, patientLiveData)
        }
    }
}