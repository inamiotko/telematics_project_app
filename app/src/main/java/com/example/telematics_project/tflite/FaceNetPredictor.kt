package com.example.telematics_project.tflite

import android.graphics.Bitmap
import com.example.telematics_project.TelematicsProjectApplication
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.nio.ByteBuffer

class FaceNetPredictor() {
    private var interpreter : Interpreter? = null

    init {
        // Initialize TFLiteInterpreter
        val interpreterOptions = Interpreter.Options().apply {
            setNumThreads( 4 )
        }
        interpreter = Interpreter(FileUtil.loadMappedFile(TelematicsProjectApplication.context, "facenet.tflite") , interpreterOptions )
    }
    val imageTensorProcessor = ImageProcessor.Builder()
        .add( ResizeOp(160,160 , ResizeOp.ResizeMethod.BILINEAR ) )
        .add( NormalizeOp( 127.5f , 127.5f ) )
        .build()

    fun convertBitmapToBuffer( image : Bitmap) : ByteBuffer {
        val imageTensor = imageTensorProcessor.process( TensorImage.fromBitmap( image ) )
        return imageTensor.buffer
    }

    fun getFaceEmbedding(image : Bitmap) : FloatArray {
        return runFaceNet( convertBitmapToBuffer( image ) )[0]
    }

    // Run the FaceNet model.
    fun runFaceNet(inputs: Any): Array<FloatArray> {
        val outputs = Array(1) { FloatArray(128 ) }
        interpreter?.run(inputs, outputs)
        return outputs
    }
}
