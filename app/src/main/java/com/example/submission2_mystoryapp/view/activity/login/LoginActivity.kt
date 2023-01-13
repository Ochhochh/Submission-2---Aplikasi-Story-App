package com.example.submission2_mystoryapp.view.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.submission2_mystoryapp.R
import com.example.submission2_mystoryapp.data.lokal.session.StorySession
import com.example.submission2_mystoryapp.view.activity.main.MainActivity
import com.example.submission2_mystoryapp.data.Result
import com.example.submission2_mystoryapp.data.remote.response.LoginResult
import com.example.submission2_mystoryapp.databinding.ActivityLoginBinding
import com.example.submission2_mystoryapp.view.activity.signup.SignUpActivity
import com.example.submission2_mystoryapp.view.customview.EmailEditText
import com.example.submission2_mystoryapp.view.customview.MyButton
import com.example.submission2_mystoryapp.view.customview.PasswordEditText

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var myButton: MyButton
    private lateinit var emailEdit: EmailEditText
    private lateinit var passwordEdit: PasswordEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val login = StorySession(this).isLogin()
        if (login){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        myButton = findViewById(R.id.btn_login)
        emailEdit = findViewById(R.id.et_email)
        passwordEdit = findViewById(R.id.et_password)

        emailEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {

            }
        })

        passwordEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {

            }
        })


        val factory: LoginViewModelFactory = LoginViewModelFactory.getInstance(this)
        val viewModel: LoginViewModel by viewModels {
            factory
        }

        binding.progressBar.visibility = View.GONE

        val pref = StorySession(this)

        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.login(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                ).observe(this@LoginActivity) {
                    if (it != null) {
                        when (it) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                pref.getUser(
                                    LoginResult(
                                        name = it.data?.name,
                                        token = it.data?.token,
                                        isLogin = true
                                    )
                                )
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                                Toast.makeText(
                                    this@LoginActivity,
                                    resources.getString(R.string.login_success),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@LoginActivity,
                                    resources.getString(R.string.login_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

            tvToSignUp.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }

        }
    }

    fun setMyButtonEnable() {
        val resultEmail = emailEdit.text
        val resultPassword = passwordEdit.text
        myButton.isEnabled = resultEmail != null && resultEmail.toString().isNotEmpty() && resultPassword != null && resultPassword.toString().isNotEmpty()
    }

}