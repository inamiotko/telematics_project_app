package com.example.telematics_project.ui

import android.net.Uri
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.telematics_project.R
import com.example.telematics_project.TelematicsProjectApplication
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentPatientEditBinding
import com.example.telematics_project.model.Patient
import com.example.telematics_project.viewmodel.PatientEditViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class PatientEditFragment : BaseFragment<FragmentPatientEditBinding, PatientEditViewModel>() {
    override val viewModel: PatientEditViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_patient_edit
    private val patientAgeList: List<Int> = (1..100).toList()
    private var spinnerPosition: Int = 0
    private var ref: StorageReference? = null
    var imagePath: String = ""
    private val storage = FirebaseStorage.getInstance()
    var storageReference: StorageReference = storage.reference
    val patientVector: List<Float> = mutableListOf(0.0f)

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
            binding.removePatientRecord.setOnClickListener {
                removePatient(patient)
                findNavController()
                    .navigate(PatientEditFragmentDirections.actionPatientEditFragmentToListViewFragment())
            }
            imagePath = patient.imagePath

        }
        val ref = storage.getReferenceFromUrl(imagePath)
        ref.downloadUrl.addOnSuccessListener(
            OnSuccessListener<Uri> { uri ->
                Glide.with(TelematicsProjectApplication.context)
                    .load(uri.toString()).into(binding.patientImage)
            }).addOnFailureListener(
            OnFailureListener {
                // Handle any errors
            })
    }

    private fun removePatient(patient: Patient) {
        viewModel.removePatient(patient)
        ref = storageReference.child(patient.imagePath)
        ref!!.delete()
    }

    private fun savePatientInfo(patient: Patient) {
        val patientId = patient.id
        val patientName = binding.editPatientName.text.toString()
        val patientAge = binding.editPatientAge.selectedItem.toString()
        val patientSymptoms = binding.editPatientSymptoms.text.toString()
        val patientConditions = binding.editPatientIllness.text.toString()
        val patientAddInfo = binding.editPatientAddInfo.text.toString()

        viewModel.updatePatientRecord(
            patientId,
            patientName,
            patientAge,
            patientSymptoms,
            patientConditions,
            patientAddInfo,
            imagePath,
            patientVector
        )
    }
}