package com.example.telematics_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.telematics_project.event.EventEmitter
import com.example.telematics_project.event.emit
import com.example.telematics_project.model.Patient
import com.example.telematics_project.repository.PatientRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class ListViewViewModel: ViewModel(), KoinComponent {
    private val patientRepo: PatientRepository by inject()
    val patients: MutableLiveData<List<Patient>> = MutableLiveData(mutableListOf())

    val patientClickedEvent = EventEmitter()

    fun start() {
        patientRepo.getPatients(patients)
    }
    fun patientClicked() {
        patientClickedEvent.emit()
    }
}