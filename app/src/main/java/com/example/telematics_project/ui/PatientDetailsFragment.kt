package com.example.telematics_project.ui

import androidx.fragment.app.viewModels
import com.example.telematics_project.R
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentPatientDetailsBinding
import com.example.telematics_project.viewmodel.PatientDetailsViewModel

class PatientDetailsFragment : BaseFragment<FragmentPatientDetailsBinding, PatientDetailsViewModel>() {
    override val viewModel: PatientDetailsViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_patient_details

    override fun initViews() {
        binding.patientName.text = "Patient full name"
        binding.patientAge.text = "Age: 16"
        binding.patientSex.text = "Sex: Female"
        binding.patientConditions.text = "Known conditions: illness1, illness2"
        binding.patientSymptoms.text = "Known symptoms: symptom1"
        binding.patientAddInfo.text = "Additional info: -"
    }

    override fun initViewModel(viewModel: PatientDetailsViewModel) {
        binding.viewModel = viewModel
    }
}