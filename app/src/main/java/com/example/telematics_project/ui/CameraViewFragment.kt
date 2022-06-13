package com.example.telematics_project.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.telematics_project.R
import com.example.telematics_project.TelematicsProjectApplication
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentCameraViewBinding
import com.example.telematics_project.model.Patient
import com.example.telematics_project.tflite.FaceNetPredictor
import com.example.telematics_project.viewmodel.CameraViewViewModel
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import java.io.File
import java.io.IOException
import java.lang.Math.round
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class CameraViewFragment : BaseFragment<FragmentCameraViewBinding, CameraViewViewModel>() {

    override val viewModel: CameraViewViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_camera_view
    private val REQUEST_IMAGE_CAPTURE = 1
    private var photoURI: Uri? = null
    private var listOfVectors: MutableList<Map<String, Any>> = mutableListOf()
    private var patients: MutableList<Patient> = mutableListOf()
    private var result: List<Float> = mutableListOf()
    private val predictor: FaceNetPredictor by inject()
    private var similarity: Double = 0.0
    var listOfSimiliarities: MutableList<Double> = mutableListOf()


    override fun initViews() {
        binding.recognisePatientButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    override fun initViewModel(viewModel: CameraViewViewModel) {
        binding.viewModel = viewModel
        viewModel.start()
        viewModel.patients.observe { patient ->
            patients.addAll(patient)
            for (p in patient) {
                listOfVectors.add(mapOf("patientId" to p.id, "imageVector" to p.vector))
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        listOfSimiliarities = mutableListOf()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFileInAppDir()
        } catch (ex: IOException) {
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            null
        }

        photoFile?.also { file ->
            photoURI = FileProvider.getUriForFile(
                requireContext(),
                "com.example.telematics_project.provider",
                file
            )

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor: ParcelFileDescriptor? =
                requireContext().contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun createImageFileInAppDir(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imagePath = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(imagePath, "JPEG_${timeStamp}_" + ".jpg")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val image = binding.img
            photoURI?.let { uri ->
                val bitmap = uriToBitmap(uri)
                bitmap?.let {
                    var patientId = ""
                    result = predictor.getFaceEmbedding(bitmap).toList()
                    val listOfSimiliarities: MutableList<Double> = mutableListOf()
                    val listOfIds: MutableList<String> = mutableListOf()

                    Glide.with(TelematicsProjectApplication.context)
                        .load(uri.toString()).fitCenter().circleCrop().into(image)
                    for (vector in listOfVectors) {
                        similarity =
                            viewModel.cosineSimilarity(result, vector.values.last() as List<Float>)
                        listOfSimiliarities.add(similarity)
                        listOfIds.add(vector.values.first() as String)
                    }
                    similarity = listOfSimiliarities.maxOf { it }
                    patientId =
                        listOfIds[listOfSimiliarities.indexOf(listOfSimiliarities.maxOf { it })]

                    for (patient in patients) {
                        if (patient.id == patientId) {
                            binding.result.text =
                                String.format(
                                    TelematicsProjectApplication.context.resources.getString(R.string.recognised_patient),
                                    patient.name, similarity
                                )
                            binding.result.visibility = View.VISIBLE
                            binding.recognisePatientButton.visibility = View.GONE
                            binding.goToProfileButton.visibility = View.VISIBLE
                            binding.goToProfileButton.setOnClickListener {
                                sharedViewModel.setPatientDetails(patient)
                                navigateToPatientDetails()
                            }
                        }
                    }
                }
            }
        }
    }
    private fun navigateToPatientDetails() {
        findNavController()
            .navigate(CameraViewFragmentDirections.actionCameraViewFragmentToPatientDetailsFragment())
    }

}