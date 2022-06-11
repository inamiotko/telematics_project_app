package com.example.telematics_project.ui

import android.net.Uri
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.telematics_project.R
import com.example.telematics_project.TelematicsProjectApplication
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentPatientDetailsBinding
import com.example.telematics_project.viewmodel.PatientDetailsViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage

class PatientDetailsFragment :
    BaseFragment<FragmentPatientDetailsBinding, PatientDetailsViewModel>() {
    override val viewModel: PatientDetailsViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_patient_details
    var imagePath: String = ""
    private val storage = FirebaseStorage.getInstance()

    override fun initViews() {
        viewModel.start(sharedViewModel.getPatientDetails())
        sharedViewModel.getPatientDetails()?.let {
            binding.patientName.text = it.name
            binding.patientAge.text = it.age
            binding.patientSex.text = it.sex
            binding.patientConditions.text = it.conditions
            binding.patientSymptoms.text = it.symptoms
            binding.patientAddInfo.text = it.add_info
            imagePath = it.imagePath
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
        binding.editPatientButton.setOnClickListener {
            navigateToPatientEdit()
        }
    }

    override fun initViewModel(viewModel: PatientDetailsViewModel) {
        binding.viewModel = viewModel
        overrideBackButton()
    }

    private fun overrideBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateToPatientList()
        }
    }

    private fun navigateToPatientList() {
        findNavController()
            .navigate(PatientDetailsFragmentDirections.actionPatientDetailsFragmentToListViewFragment())
    }

    private fun navigateToPatientEdit() {
        findNavController()
            .navigate(PatientDetailsFragmentDirections.actionPatientDetailsFragmentToPatientEditFragment())
    }
}