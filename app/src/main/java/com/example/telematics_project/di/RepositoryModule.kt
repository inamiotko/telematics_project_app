package com.example.telematics_project.di

import com.example.telematics_project.repository.PatientRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PatientRepository() }
}