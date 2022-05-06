package com.example.telematics_project.repository

import androidx.lifecycle.MutableLiveData
import com.example.telematics_project.model.Patient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
}