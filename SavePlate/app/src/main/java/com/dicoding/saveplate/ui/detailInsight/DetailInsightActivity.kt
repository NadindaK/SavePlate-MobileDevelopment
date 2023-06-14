package com.dicoding.saveplate.ui.detailInsight

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.dicoding.saveplate.R
import com.dicoding.saveplate.data.Insights
import com.dicoding.saveplate.databinding.ActivityDetailInsightBinding
import com.dicoding.saveplate.databinding.ActivityProfileBinding
import com.dicoding.saveplate.ui.profile.ProfileViewModel

class DetailInsightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailInsightBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInsightBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            title = getString(R.string.insight)
        }

    }

    private fun setupData(){

        val insight = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DETAIL_INSIGHT, Insights::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DETAIL_INSIGHT)
        }

        if (insight != null) {
            binding.tvTitle.text = insight.title
        }
        if (insight != null) {
            binding.tvSource.text = insight.source
        }
        if (insight != null) {
            binding.tvArticle.text = insight.article
        }
    }

    companion object {
        const val DETAIL_INSIGHT = ""
    }
}