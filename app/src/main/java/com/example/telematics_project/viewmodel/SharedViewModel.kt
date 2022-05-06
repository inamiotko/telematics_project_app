package com.example.telematics_project.viewmodel

import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModel
import com.example.telematics_project.R
import com.example.telematics_project.model.Patient

class SharedViewModel : ViewModel() {

    private var patientData: Patient? = null
    var pickedSex: String = ""

    fun setPatientDetails(patient: Patient) {
        patientData = patient
    }
    fun onPickAnswerFemale() {
        pickedSex = "Female"
    }

    fun onPickAnswerMale() {
        pickedSex = "Male"
    }

    fun onPickAnswerOther() {
        pickedSex = "Other"
    }

}