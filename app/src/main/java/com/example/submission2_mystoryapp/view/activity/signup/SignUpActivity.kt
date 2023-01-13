package com.example.submission2_mystoryapp.view.activity.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.submission2_mystoryapp.R
import com.example.submission2_mystoryapp.databinding.ActivitySignUpBinding
import com.example.submission2_mystoryapp.view.activity.login.LoginActivity
import com.example.submission2_mystoryapp.data.Result

class SignUpActivity : AppCompatActivity() {
    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory: SignUpViewModelFactory = SignUpViewModelFactory.getInstance(this)
        val viewModel: SignUpViewModel by viewModels {
            factory
        }
        binding.progressBar.visibility = View.GONE

        binding.apply {
            btnSignup.setOnClickListener {
                viewModel.signup(
                    etUsername.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString()
                ).observe(this@SignUpActivity) {
                    if (it != null) {
                        when (it) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                Log.e("CEk", "Tess Data")
                                binding.progressBar.visibility = View.GONE
                                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this@SignUpActivity,
                                    resources.getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(this@SignUpActivity,
                                    resources.getString(R.string.register_error), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

            tvToLogin.setOnClickListener {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}