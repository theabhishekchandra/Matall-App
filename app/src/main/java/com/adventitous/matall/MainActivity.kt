package com.adventitous.matall

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.modules.logins.viewmodel.FirebaseViewModel
import com.adventitous.matall.modules.model.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navigation: NavController
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    @Inject lateinit var appSharedPreferences: AppSharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize navigation
        setupNavigation()

        // Set default night mde to avoid dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (appSharedPreferences.getUserLoggedIn()) {
            navigation.navigate(R.id.homeFragment)
            navigation.popBackStack(R.id.homeFragment, false)

        }else{
            navigation.navigate(R.id.signIn)
            navigation.popBackStack(R.id.signIn, false)
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navigation = navHostFragment.navController

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.setupWithNavController(navigation)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigation.navigateUp() || super.onSupportNavigateUp()
    }

    fun getNavController(): NavController {
        return navigation
    }
}
