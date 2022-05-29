package com.example.telematics_project.ui

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.telematics_project.R
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentPatientEditBinding
import com.example.telematics_project.model.Patient
import com.example.telematics_project.viewmodel.PatientEditViewModel

class PatientEditFragment : BaseFragment<FragmentPatientEditBinding, PatientEditViewModel>() {
    override val viewModel: PatientEditViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_patient_edit
    private val patientAgeList: List<Int> = (1..100).toList()
    private var spinnerPosition: Int = 0

    override fun initViewModel(viewModel: PatientEditViewModel) {
        binding.viewModel = viewModel
    }

    override fun initViews() {
        sharedViewModel.getPatientDetails()?.let { patient ->
            binding.editPatientAge.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                patientAgeList
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.editPatientAge.adapter = adapter
                spinnerPosition = adapter.getPosition(patient.age.toInt())
            }
            binding.editPatientName.setText(patient.name)
            binding.editPatientAge.setSelection(spinnerPosition)
            when (patient.sex) {
                "Female" -> binding.editPatientSex.check(R.id.radio_female)
                "Male" -> binding.editPatientSex.check(R.id.radio_male)
                "Other" -> binding.editPatientSex.check(R.id.radio_other)
            }
            binding.editPatientIllness.setText(patient.conditions)
            binding.editPatientSymptoms.setText(patient.symptoms)
            binding.editPatientAddInfo.setText(patient.add_info)
            binding.updatePatientRecord.setOnClickListener {
                savePatientInfo(patient)
                findNavController()
                    .navigate(PatientEditFragmentDirections.actionPatientEditFragmentToListViewFragment())
            }
        }
    }

    private fun savePatientInfo(patient: Patient) {
        val patientId = patient.id
        val patientName = binding.editPatientName.text.toString()
        val patientAge = binding.editPatientAge.selectedItem.toString()
        val patientSymptoms = binding.editPatientSymptoms.text.toString()
        val patientConditions = binding.editPatientIllness.text.toString()
        val patientAddInfo = binding.editPatientAddInfo.text.toString()
        val patientImagePath = binding.patientImage

        viewModel.updatePatientRecord(
            patientId,
            patientName,
            patientAge,
            patientSymptoms,
            patientConditions,
            patientAddInfo,
            "path"
        )
    }
}