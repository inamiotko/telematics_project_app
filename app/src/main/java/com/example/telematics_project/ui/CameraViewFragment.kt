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
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.example.telematics_project.R
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentCameraViewBinding
import com.example.telematics_project.viewmodel.CameraViewViewModel
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*


class CameraViewFragment : BaseFragment<FragmentCameraViewBinding, CameraViewViewModel>() {

    override val viewModel: CameraViewViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_camera_view
    private var cameraPhotoFilePath: Bitmap? = null
    private val REQUEST_IMAGE_CAPTURE = 1
    private var interpreter : Interpreter? = null
    private var photoURI: Uri? = null

    private val imageTensorProcessor = ImageProcessor.Builder()
        .add( ResizeOp(160,160 , ResizeOp.ResizeMethod.BILINEAR ) )
        .add( NormalizeOp( 127.5f , 127.5f ) )
        .build()

    private fun convertBitmapToBuffer( image : Bitmap) : ByteBuffer {
        val imageTensor = imageTensorProcessor.process( TensorImage.fromBitmap( image ) )
        return imageTensor.buffer
    }

    private fun getFaceEmbedding(image : Bitmap) : FloatArray {

        return runFaceNet( convertBitmapToBuffer( image ) )[0]
    }

    // Run the FaceNet model.
    private fun runFaceNet(inputs: Any): Array<FloatArray> {
        val outputs = Array(1) { FloatArray(128 ) }
        Log.i("###!!", "$outputs")
        interpreter?.run(inputs, outputs)
        return outputs
    }

    override fun initViews() {
        // Initialize TFLiteInterpreter
        val interpreterOptions = Interpreter.Options().apply {
            setNumThreads( 4 )
        }
        interpreter = Interpreter(FileUtil.loadMappedFile(requireContext(), "facenet.tflite") , interpreterOptions )

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
            photoURI?.let{
                val bitmap = uriToBitmap(it)
                bitmap?.let {
                    getFaceEmbedding(bitmap)
                    image.setImageBitmap(bitmap)

                }
                Log.i("###", "$bitmap")
            }

            // todo recognise patient
//                        cameraPhotoFilePath?.let { getFaceEmbedding(it) }



        }
    }
}