package com.example.submission2_mystoryapp.view.activity.story

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.submission2_mystoryapp.R
import com.example.submission2_mystoryapp.data.lokal.session.StorySession
import com.example.submission2_mystoryapp.data.remote.response.LoginResult
import com.example.submission2_mystoryapp.databinding.ActivityStoryBinding
import com.example.submission2_mystoryapp.view.activity.main.MainActivity
import com.example.submission2_mystoryapp.view.createCustomTempFile
import com.example.submission2_mystoryapp.view.reduceFileImage
import com.example.submission2_mystoryapp.view.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import com.example.submission2_mystoryapp.data.Result
import com.example.submission2_mystoryapp.view.rotateBitmap
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryActivity : AppCompatActivity() {
    private var _binding: ActivityStoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user: LoginResult = StorySession(this).getToken()
        val factory: StoryViewModelFactory = StoryViewModelFactory.getInstance(this)
        val viewModel: StoryViewModel by viewModels {
            factory
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding.progressBar.visibility = View.GONE

        binding.apply {
            btnCamera.setOnClickListener {
                @SuppressLint("QueryPermissionsNeeded")
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.resolveActivity(packageManager)

                createCustomTempFile(application).also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this@StoryActivity,
                        "com.example.submission2_mystoryapp",
                        it
                    )
                    currentPhotoPath = it.absolutePath
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    launcherIntentCamera.launch(intent)
                }
            }

            btnGalery.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                launcherIntentGallery.launch(chooser)
            }

            btnSubmit.setOnClickListener {
                if (getFile != null && etDesc.text.toString().isNotEmpty()) {
                    val file = reduceFileImage(getFile as File)
                    val description = etDesc.text.toString().toRequestBody("text/plain".toMediaType())
                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "photo",
                        file.name,
                        requestImageFile
                    )

                    viewModel.addNewStory("Bearer ${user.token}", imageMultipart, description)
                        .observe(this@StoryActivity) {
                            if (it != null) {
                                when (it) {
                                    is Result.Loading -> {
                                        binding.progressBar.visibility = View.VISIBLE
                                    }
                                    is Result.Success -> {
                                        binding.progressBar.visibility = View.GONE
                                        val intent = Intent(this@StoryActivity, MainActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                        Toast.makeText(
                                            this@StoryActivity,
                                            resources.getString(R.string.story_success),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    is Result.Error -> {
                                        binding.progressBar.visibility = View.GONE
                                        Toast.makeText(
                                            this@StoryActivity,
                                            resources.getString(R.string.story_error),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                } else {
                    Toast.makeText(
                        this@StoryActivity,
                        resources.getString(R.string.story_validate),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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
                    resources.getString(R.string.permissions),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path)
            )
            binding.ivImage.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@StoryActivity)
            getFile = myFile
            binding.ivImage.setImageURI(selectedImg)
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}