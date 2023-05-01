package com.bassul.flavorfusion.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.bassul.flavorfusion.R
import com.bassul.flavorfusion.databinding.ActivityRecipesBinding

class RecipesActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_post_container) as NavHostFragment

        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf()
        )

    }
}