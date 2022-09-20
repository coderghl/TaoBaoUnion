package com.coderghl.taobaounion.ui.activity

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.coderghl.taobaounion.R
import com.coderghl.taobaounion.base.BaseActivity
import com.coderghl.taobaounion.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }

}