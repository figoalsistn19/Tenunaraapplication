@file:Suppress("DEPRECATION")

package com.example.tenunaraapplication.main.ui.ui.scanwoven

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.ActivityScanWovenFabricBinding
import com.example.tenunaraapplication.main.ui.rotateBitmap
import com.example.tenunaraapplication.main.ui.ui.camera.CameraActivity
import com.example.tenunaraapplication.main.ui.uriToFile
//import com.example.tenunaraapplication.ml.ConvertedModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File

class ScanWovenFabricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanWovenFabricBinding
    private var getFile: File? = null
    private lateinit var bitmap: Bitmap


    @SuppressLint("NewApi")
    fun checkandGetpermissions(){
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
        }
        else{
            Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanWovenFabricBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val wovenList = application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split("\n")

        checkandGetpermissions()

        binding.btnGallery.setOnClickListener{
            startGallery()
        }

//        binding.btnScan.setOnClickListener{
//
//                val resized: Bitmap = Bitmap.createScaledBitmap(
//                    bitmap,
//                    300,
//                    300,
//                    true
//                )
//                val model = ConvertedModel.newInstance(this  )
//
//
//                val tbuffer = TensorImage.fromBitmap(resized)
//                val byteBuffer = tbuffer.buffer
//
//                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 300, 300, 3), DataType.FLOAT32)
//                inputFeature0.loadBuffer(byteBuffer)
//
//                val outputs = model.process(inputFeature0)
//                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//                val max = getMax(outputFeature0.floatArray)
//
//                val tvResult: TextView = findViewById(R.id.tv_result)
//                tvResult.text = wovenList[max]
//
//                model.close()
//
//        }


        binding.btnCamera.setOnClickListener {
            startCamera()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 250){
            binding.imgToScan?.setImageURI(data?.data)

            val uri : Uri ?= data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }
        else if(requestCode == 200 && resultCode == Activity.RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
            binding.imgToScan?.setImageBitmap(bitmap)
        }
    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            getFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding.imgToScan?.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ScanWovenFabricActivity)
            getFile = myFile
            binding.imgToScan?.setImageURI(selectedImg)
        }
    }

//    private fun getMax(arr:FloatArray) : Int{
//        var ind = 0
//        var min = 0.0f
//        for(i in 0..1000)
//        {
//            if(arr[i] > min)
//            {
//                min = arr[i]
//                ind = i
//            }
//        }
//        return ind
//    }

    companion object {
        const val CAMERA_X_RESULT = 200
    }
}