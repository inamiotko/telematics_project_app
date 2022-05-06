package com.example.telematics_project.di

import android.widget.ListView
import com.example.telematics_project.viewmodel.CameraViewViewModel
import com.example.telematics_project.viewmodel.ListViewViewModel
import com.example.telematics_project.viewmodel.PatientDetailsViewModel
import com.example.telematics_project.viewmodel.SharedViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {SharedViewModel()}
    viewModel { CameraViewViewModel() }
    viewModel { ListViewViewModel() }
    viewModel { PatientDetailsViewModel() }
}