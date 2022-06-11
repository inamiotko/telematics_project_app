package com.example.telematics_project.repository

import androidx.lifecycle.MutableLiveData
import com.example.telematics_project.model.Patient
import com.google.firebase.database.*

class PatientRepository() {
    private val database = FirebaseDatabase.getInstance().reference

    fun getPatients(liveData: MutableLiveData<List<Patient>>) {
        val quizReference = database.child("patients")
        quizReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                liveData.value =
                    dataSnapshot.children.mapNotNull { it.getValue(Patient::class.java) }.toList()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getPatient(patientId: String, liveData: MutableLiveData<Patient>) {
        val quizReference = database.child("patients/${patientId}")
        quizReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                liveData.value =
                    dataSnapshot.getValue(Patient::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun updatePatientData(
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
        lateinit var quizReference: DatabaseReference
        val patientRecord = Patient(
            patientId,
            name,
            age,
            sex,
            symptoms,
            conditions,
            add_info,
            imagePath,
            vector
        )
        patientId.let {
            quizReference = database.child("patients/${patientId}/")
            quizReference.setValue(patientRecord)
        }
    }

    fun createPatient(
        patientId: String,
        name: String,
        age: String,
        sex: String,
        symptoms: String,
        conditions: String,
        add_info: String,
        imagePath: String,
        imageVector: List<Float>
    ) {
        val patientRecord = mutableMapOf<String, Any>(
            "id" to patientId,
            "name" to name,
            "age" to age,
            "sex" to sex,
            "symptoms" to symptoms,
            "conditions" to conditions,
            "add_info" to add_info,
            "imagePath" to imagePath,
            "vector" to imageVector
        )
        val quizReference = database.child("patients/${patientId}")
        quizReference.updateChildren(patientRecord)
    }

    fun deletePatient(patientId: String) {
        val quizReference: DatabaseReference =
            database.child("patients/${patientId}")
        quizReference.removeValue()
    }
}