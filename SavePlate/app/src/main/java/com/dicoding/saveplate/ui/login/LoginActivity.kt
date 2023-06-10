package com.dicoding.saveplate.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.dicoding.saveplate.MainActivity
import com.dicoding.saveplate.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin: Button = findViewById(R.id.login_button)
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_login -> {
                val move = Intent(this@LoginActivity, MainActivity::class.java)
//                moveData.putExtra(AboutPage.NAME, "Nadinda Kalila")
//                moveData.putExtra(AboutPage.EMAIL, "nadinda.kalila@ui.ac.id")
                startActivity(move)
            }
        }
    }


}