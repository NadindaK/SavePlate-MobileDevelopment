package com.dicoding.saveplate.ui.register

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.saveplate.R
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.databinding.ActivityRegisterBinding
import com.dicoding.saveplate.ui.ViewModelFactory
import com.dicoding.saveplate.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.apply {
            title = getString(R.string.register)
        }
    }

    private fun setupViewModel() {
        registerViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[RegisterViewModel::class.java]
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val username = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val confPassword = binding.edRegisterConfirmPassword.text.toString()
            when {
                username.isEmpty() -> {
                    binding.edRegisterName.error = "Username tidak boleh kosong"
                }
                email.isEmpty() -> {
                    binding.edRegisterEmail.error = "Email tidak boleh kosong"
                }
                password.isEmpty() -> {
                    binding.edRegisterPassword.error = "Password tidak boleh kosong"
                }
                confPassword != password -> {
                    binding.edRegisterConfirmPassword.error = "Konfirmasi password belum sesuai"
                }
                else -> {
                    registerViewModel.postRegister(username, email, password, confPassword).observe(this){ result ->

                            AlertDialog.Builder(this).apply {
                                setTitle("Register Berhasil!")
                                setMessage("Silakan login untuk masuk ke aplikasi")
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(context, LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }

                    }

                }
            }
        }
}


