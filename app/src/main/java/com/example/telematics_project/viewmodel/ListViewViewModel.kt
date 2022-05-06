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
    val patientRepo: PatientRepository by inject()
    val data: MutableLiveData<List<Patient>> = MutableLiveData(mutableListOf())


    val patientClickedEvent = EventEmitter()
//    val quizRepository: QuizRepository by inject()

    fun start() {
//        quizRepository.getQuizzes(quizzes)
    }
    fun patientClicked() {
        patientClickedEvent.emit()
        patientRepo.getPatients(data)
        Log.i("###", data.value.toString())
    }
}