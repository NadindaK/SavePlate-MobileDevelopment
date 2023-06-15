package com.dicoding.saveplate.ui.recycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.saveplate.R
import com.dicoding.saveplate.data.Insights
import com.dicoding.saveplate.data.Recycle
import com.dicoding.saveplate.databinding.ActivityLandingBinding
import com.dicoding.saveplate.databinding.ActivityLoginBinding
import com.dicoding.saveplate.databinding.ActivityRecycleBinding
import com.dicoding.saveplate.ui.adapter.ListInsightsAdapter
import com.dicoding.saveplate.ui.adapter.ListRecycleAdapter

class RecycleActivity : AppCompatActivity() {

    private lateinit var listInsights: RecyclerView
    private val list = ArrayList<Recycle>()
    private lateinit var binding: ActivityRecycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listInsights = binding.rvRecycle
        listInsights.setHasFixedSize(true)

        supportActionBar?.title = "Recycle Idea"

        list.addAll(getListInsights())
        showRecyclerList()
    }

    private fun getListInsights(): ArrayList<Recycle> {
        val dataTitle = resources.getStringArray(R.array.data_title2)
        val dataArticle = resources.getStringArray(R.array.data_article2)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo2)
        val dataSource = resources.getStringArray(R.array.data_source2)
        val listInsights = ArrayList<Recycle>()
        for (i in dataTitle.indices) {
            val insights = Recycle(dataTitle[i], dataSource[i], dataPhoto.getResourceId(i, -1), dataArticle[i])
            listInsights.add(insights)
        }
        return listInsights
    }

    private fun showRecyclerList() {
        listInsights.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListRecycleAdapter(list)
        listInsights.adapter = listHeroAdapter
    }
}