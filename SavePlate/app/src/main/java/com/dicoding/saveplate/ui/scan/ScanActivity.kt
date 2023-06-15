package com.dicoding.saveplate.ui.scan

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowInsets
import android.view.WindowManager
import android.hardware.Camera
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
//import androidx.camera.core.Camera
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import android.Manifest
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.MenuItem
import com.dicoding.saveplate.MainActivity
import com.dicoding.saveplate.R
//import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.databinding.ActivityProfileBinding
import com.dicoding.saveplate.databinding.ActivityScanBinding
import com.dicoding.saveplate.retrofit.ApiConfig
import com.dicoding.saveplate.ui.ViewModelFactory
import com.dicoding.saveplate.ui.profile.ProfileViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.response.ScanResponse
import com.dicoding.saveplate.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationBarView

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ScanActivity : AppCompatActivity() {

    private var _binding: ActivityScanBinding? = null
    private val binding get() = _binding!!

    private var getFile: File? = null
    private lateinit var bottomNavigationView: BottomNavigationView

//    private var userPreference: UserPreference

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Check Food"

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#44746D")))

        bottomNavigationView = binding.navView
        bottomNavigationView.setOnItemSelectedListener { item ->
            val intentHome = Intent(this, MainActivity::class.java)
            val intentProfile = Intent(this, ProfileActivity::class.java)
            when (item.itemId) {
                R.id.home -> {
                    startActivity(intentHome)
                    true}
                R.id.scan -> {
                    true}
                R.id.profile -> {
                    startActivity(intentProfile)
                    true}
            }
            false
        }

        bottomNavigationView.selectedItemId = R.id.scan

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.cameraBtn.setOnClickListener { startTakePhoto() }
        binding.galleryBtn.setOnClickListener { startGallery() }
        binding.checkBtn.setOnClickListener {
            showLoading(true)
            uploadImage()
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@ScanActivity,
                "com.dicoding.saveplate",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun uploadImage() {

        val userPreference = UserPreference(dataStore)

        val auth = "Bearer "+ userPreference.getUser()

        if (getFile != null) {

            val file = reduceFileImage(getFile as File)

//            val description = binding.deskripsiCerita.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )

            showLoading(true)

            val apiService = ApiConfig.getApiServiceScan()
            val uploadImageRequest = apiService.scan(imageMultipart)
            uploadImageRequest.enqueue(object : Callback<ScanResponse> {
                override fun onFailure(call: Call<ScanResponse>, t: Throwable) {

                    showLoading(false)

                    Toast.makeText(this@ScanActivity, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ScanResponse>,
                    response: Response<ScanResponse>
                ) {
                    if (response.isSuccessful) {

                        showLoading(false)

                        val responseBody = response.body()
                        if (responseBody != null) {
                            Toast.makeText(this@ScanActivity, "Check food successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ScanActivity, ResultActivity::class.java)

//                            val confidence = responseBody.confidence as? Double ?: 0

//                            val result = response.errorBody()?.string()?.let { JSONObject(it) }
                            intent.putExtra("confidence", responseBody.confidence.toString())
                            intent.putExtra("label", responseBody.label)

                            startActivity(intent)
                            finish()
                        }
                    } else {

                        showLoading(false)

                        Toast.makeText(this@ScanActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {

            showLoading(false)

            Toast.makeText(this@ScanActivity, "Please put a picture first.", Toast.LENGTH_SHORT).show()
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.imageFood.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {

        val cameraInfo = Camera.CameraInfo()
        val cameraId = intent.extras?.getInt("android.intent.extras.CAMERA_FACING") ?: Camera.CameraInfo.CAMERA_FACING_BACK
        Camera.getCameraInfo(cameraId, cameraInfo)

        val isBackCamera = cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK

        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.imageFood.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@ScanActivity)
                getFile = myFile
                binding.imageFood.setImageURI(uri)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        val intentHome = Intent(this, MainActivity::class.java)
//        val intentProfile = Intent(this, ProfileActivity::class.java)
//        when (item.itemId) {
//            R.id.home -> startActivity(intentHome)
//            R.id.profile -> startActivity(intentProfile)
//        }
//        return super.onOptionsItemSelected(item)
//    }
}