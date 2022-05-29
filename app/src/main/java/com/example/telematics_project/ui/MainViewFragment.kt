package com.example.telematics_project.ui

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.telematics_project.R
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentMainViewBinding
import com.example.telematics_project.viewmodel.SharedViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainViewFragment : BaseFragment<FragmentMainViewBinding, SharedViewModel>() {
    override val viewModel: SharedViewModel by viewModels()
    private val REQUEST_IMAGE_CAPTURE = 1
    private val patientAgeList: List<Int> = (1..100).toList()
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageReference: StorageReference = storage.reference
    var cameraPhotoFilePath: Uri? = null
    private var patientId: String = ""
    private var ref: StorageReference? = null

    override fun getLayoutId(): Int = R.layout.fragment_main_view
    override fun initViews() {
        binding.addPatientAge.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            patientAgeList
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.addPatientAge.adapter = adapter
        }
        binding.addPatientImage.setOnClickListener {
            dispatchTakePictureIntent()
        }
        binding.addPatientButton.setOnClickListener {
            addPatientInfo()
            findNavController().navigate(MainViewFragmentDirections.actionMainViewFragmentToListViewFragment())
        }
        patientId = (0..100000).random().toString()
    }

    override fun initViewModel(viewModel: SharedViewModel) {
        binding.viewModel = viewModel
    }

    private fun addPatientInfo() {
        val patientName = binding.addPatientName.text.toString()
        val patientAge = binding.addPatientAge.selectedItem.toString()
        val patientSymptoms = binding.addPatientSymptoms.text.toString()
        val patientConditions = binding.addPatientIllness.text.toString()
        val patientAddInfo = binding.addPatientAddInfo.text.toString()
        val patientImagePath = ref.toString()

        viewModel.addPatientRecord(
            patientId,
            patientName,
            patientAge,
            patientSymptoms,
            patientConditions,
            patientAddInfo,
            patientImagePath
        )
    }


    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFileInAppDir()
        } catch (ex: IOException) {
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            null
        }

        photoFile?.also { file ->
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.telematics_project.provider",
                file
            )
            cameraPhotoFilePath = photoURI
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createImageFileInAppDir(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imagePath = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(imagePath, "JPEG_${timeStamp}_" + ".jpg")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            uploadImage(cameraPhotoFilePath)
            Glide.with(requireContext()).load(cameraPhotoFilePath).into(binding.addPatientImage)
        }
    }

    private fun uploadImage(filePath: Uri?) {
        patientId = (0..100000).random().toString()
        filePath?.let {
            val progressDialog = ProgressDialog(requireContext())
            progressDialog.setTitle("Uploading image to database...")
            progressDialog.show()
            ref = storageReference.child("images/" + patientId + "/" + UUID.randomUUID() +".jpg")
            ref!!.putFile(it)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "Failed " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }
    }
}