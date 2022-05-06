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
//        TODO("Not yet implemented")
    }

    override fun initViewModel(viewModel: PatientDetailsViewModel) {
        binding.viewModel = viewModel
    }
}