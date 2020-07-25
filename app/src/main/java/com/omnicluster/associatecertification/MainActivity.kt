package com.omnicluster.associatecertification

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.omnicluster.associatecertification.databinding.ActivityMainBinding
import com.omnicluster.associatecertification.util.Utils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Utils.onActivityCreateSetTheme(this)
        }
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigationGraph()
    }

    private fun setupNavigationGraph() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        navHostFragment?.let {
            navController = it.navController
            navController.graph = navController.navInflater.inflate(R.navigation.nav_main)

            supportActionBar?.let {
                NavigationUI.setupActionBarWithNavController(this, navController)
            }
        } ?: throw IllegalStateException()
    }
}