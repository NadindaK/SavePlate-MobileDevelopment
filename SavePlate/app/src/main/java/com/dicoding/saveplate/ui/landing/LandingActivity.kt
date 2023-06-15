package com.dicoding.saveplate.ui.landing

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.dicoding.saveplate.R
import com.dicoding.saveplate.databinding.ActivityLandingBinding
import com.dicoding.saveplate.ui.login.LoginActivity
import com.dicoding.saveplate.ui.register.RegisterActivity

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        supportActionBar?.hide()

        setupView()
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
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.ivPhoto, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.buttonLogin, View.ALPHA, 1f).setDuration(500)
        val acc = ObjectAnimator.ofFloat(binding.tvTitle2, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.buttonRegister, View.ALPHA, 1f).setDuration(500)


//        val together = AnimatorSet().apply {
//            playTogether(login, register)
//        }


        AnimatorSet().apply {
            playSequentially(
                title,
                login,
                acc,
                register,
            )
            startDelay = 500
            start()
        }
    }
}