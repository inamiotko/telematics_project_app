package com.example.telematics_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.telematics_project.base.BottomBarConfiguration.Companion.configuration
import com.example.telematics_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle(R.string.title_activity_main_activity)
        setupNavigation()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.activity_main_navigation_host)
        binding.activityMainBottomNavigation.setupWithNavController(navController)
        binding.activityMainBottomNavigation.background = null
        setupActionBarWithNavController(navController, configuration)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}