package com.example.telematics_project.di

import com.example.telematics_project.repository.PatientRepository
import com.example.telematics_project.tflite.FaceNetPredictor
import org.koin.dsl.module

val repositoryModule = module {
    single { PatientRepository() }
    single { FaceNetPredictor() }
}