package com.example.telematics_project

import android.app.Application
import com.example.telematics_project.di.repositoryModule
import com.example.telematics_project.di.viewModelModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TelematicsProjectApplication : Application() {
    companion object {
        lateinit var context: TelematicsProjectApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        initFirebase()
        setUpKoin()
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun setUpKoin() {
        startKoin {
            androidLogger()
            androidContext(this@TelematicsProjectApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}