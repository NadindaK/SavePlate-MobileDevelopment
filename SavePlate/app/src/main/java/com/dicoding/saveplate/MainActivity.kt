package com.dicoding.saveplate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.saveplate.data.Insights
import com.dicoding.saveplate.data.UserPreference
import com.dicoding.saveplate.databinding.ActivityMainBinding
import com.dicoding.saveplate.ui.ViewModelFactory
import com.dicoding.saveplate.ui.adapter.ListInsightsAdapter
import com.dicoding.saveplate.ui.landing.LandingActivity
import com.dicoding.saveplate.ui.profile.ProfileActivity
import com.dicoding.saveplate.ui.scan.ScanActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var listInsights: RecyclerView
    private val list = ArrayList<Insights>()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listInsights = binding.insightsList
        listInsights.setHasFixedSize(true)

        val btnAboutPage: Button = binding.btnScan
        btnAboutPage.setOnClickListener(this)

        supportActionBar?.title = "Home"

        setupViewModel()

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_scan -> {
                val intentScan = Intent(this, ScanActivity::class.java)
                startActivity(intentScan)
            }
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore),this)
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (!user.isLogin){
                startActivity(Intent(this, LandingActivity::class.java))
                finish()
            } else {
                list.addAll(getListInsights())
                showRecyclerList()
            }
        }
    }

    private fun getListInsights(): ArrayList<Insights> {
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataArticle = resources.getStringArray(R.array.data_article)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataSource = resources.getStringArray(R.array.data_source)
        val listInsights = ArrayList<Insights>()
        for (i in dataTitle.indices) {
            val insights = Insights(dataTitle[i], dataSource[i], dataPhoto.getResourceId(i, -1), dataArticle[i])
            listInsights.add(insights)
        }
        return listInsights
    }

    private fun showRecyclerList() {
        listInsights.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListInsightsAdapter(list)
        listInsights.adapter = listHeroAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intentProfile = Intent(this, ProfileActivity::class.java)
        when (item.itemId) {
            R.id.profile -> startActivity(intentProfile)
            R.id.logout -> mainViewModel.logout()

        }
        return super.onOptionsItemSelected(item)
    }
}