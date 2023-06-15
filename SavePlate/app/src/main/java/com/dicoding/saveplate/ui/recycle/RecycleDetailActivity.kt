package com.dicoding.saveplate.ui.recycle

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.dicoding.saveplate.data.Recycle
import com.dicoding.saveplate.databinding.ActivityRecycleDetailBinding

class RecycleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecycleDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecycleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
        setupData()
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
            title = "Recycle"
        }

    }

    private fun setupData(){

        val insight = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DETAIL_RECYCLE, Recycle::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DETAIL_RECYCLE)
        }

        if (insight != null) {
            binding.tvTitle.text = insight.title

            binding.tvSource.text = insight.source

            binding.tvArticle.text = insight.article

            binding.imagePoster.setImageResource(insight.photo)
        }
    }

    companion object {
        const val DETAIL_RECYCLE = ""
    }
}