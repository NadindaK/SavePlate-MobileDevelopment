package com.dicoding.saveplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.saveplate.data.Insights
import com.dicoding.saveplate.ui.adapter.ListInsightsAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var listInsights: RecyclerView
    private val list = ArrayList<Insights>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listInsights = findViewById(R.id.insights_list)
        listInsights.setHasFixedSize(true)

        list.addAll(getListInsights())
        showRecyclerList()

//        val btnAboutPage: Button = findViewById(R.id.btn_about_page)
//        btnAboutPage.setOnClickListener(this)

    }

    private fun getListInsights(): ArrayList<Insights> {
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataArticle = resources.getStringArray(R.array.data_article)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataSource = resources.getStringArray(R.array.data_source)
        val listConcert = ArrayList<Insights>()
        for (i in dataTitle.indices) {
            val insights = Insights(dataTitle[i], dataArticle[i], dataPhoto.getResourceId(i, -1), dataSource[i])
            listConcert.add(insights)
        }
        return listConcert
    }

    private fun showRecyclerList() {
        listInsights.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListInsightsAdapter(list)
        listInsights.adapter = listHeroAdapter
    }
}