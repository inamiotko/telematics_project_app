package com.example.telematics_project.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.telematics_project.R
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentCameraViewBinding
import com.example.telematics_project.viewmodel.CameraViewViewModel
import com.example.telematics_project.viewmodel.ListViewViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraViewFragment : BaseFragment<FragmentCameraViewBinding, CameraViewViewModel>() {

    override val viewModel: CameraViewViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_camera_view
    private var cameraPhotoFilePath: Uri? = null
    private val REQUEST_IMAGE_CAPTURE = 1


    override fun initViews() {
        binding.recognisePatientButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    override fun initViewModel(viewModel: CameraViewViewModel) {
        binding.viewModel = viewModel
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // todo recognise patient
        }
    }
}