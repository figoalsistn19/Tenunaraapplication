@file:Suppress("DEPRECATION")

package com.example.tenunaraapplication.main.ui.ui.scanwoven

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.ActivityScanWovenFabricBinding
import com.example.tenunaraapplication.ml.ConvertedModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class ScanWovenFabricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanWovenFabricBinding
    private lateinit var bitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_woven_fabric)

        val fileName = "label.txt"
        val inputString = application.assets.open(fileName).bufferedReader().use { it.readText() }
        val wovenList = inputString.split("\n")

        binding.btnCamera.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent,100)
        }

        binding.btnScan.setOnClickListener{
            val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true)

            val model = ConvertedModel.newInstance(this  )

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 300, 300, 3), DataType.FLOAT32)
            val tbuffer = TensorImage.fromBitmap(resized)
            val byteBuffer = tbuffer.buffer
            inputFeature0.loadBuffer(byteBuffer)


            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val max = getMax(outputFeature0.floatArray)

            val tvResult: TextView = findViewById(R.id.tv_result)
            tvResult.text = wovenList[max]


            model.close()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        binding.imgToScan?.setImageURI(data?.data)

        val uri: Uri?= data?.data

        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
    }

    private fun getMax(arr:FloatArray): Int {

        var ind = 0
        var min = 0.0f

        for (i in 0..1000)
        {
            if (arr[i]> min)
            {
                ind= i
                min = arr[i]
            }
        }
        return ind
    }

}