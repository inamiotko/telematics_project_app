package com.example.telematics_project

import android.app.Application
import com.google.firebase.FirebaseApp

class TelematicsProjectApplication : Application() {
    companion object {
        lateinit var context: TelematicsProjectApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        initFirebase()
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }
}