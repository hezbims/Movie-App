package com.example.finalprojectsekolahbeta1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.finalprojectsekolahbeta1.databinding.ActivityMainBinding
import com.example.finalprojectsekolahbeta1.authentication.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener
    private lateinit var binding : ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.mainFragment , R.id.genresFragment) ,
            drawerLayout
        )
        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment)
                .navController

        setAuthStateListener()
        NavigationUI.setupActionBarWithNavController(
            this ,
            navController ,
            appBarConfiguration
        )
        setNavigationDrawerItemSelectedListener()
        setDestinationChangedListener()
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setAuthStateListener(){
        authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) { // user belum login
                startActivity(
                    Intent(this@MainActivity , LoginActivity::class.java)
                )
                this@MainActivity.finish()
            }
        }
    }

    private fun setNavigationDrawerItemSelectedListener(){
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.logout -> Firebase.auth.signOut()
                R.id.favorite -> navController.navigate(R.id.favoriteFragment)
            }
            true
        }
    }

    private fun setDestinationChangedListener(){
        navController.addOnDestinationChangedListener{
                _ , destination , _ ->
            binding.bottomNavigationView.visibility =
                when(destination.id){
                    R.id.mainFragment , R.id.genresFragment -> {
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                        View.VISIBLE
                    }
                    else -> {
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                        View.GONE
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        Firebase.auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        Firebase.auth.removeAuthStateListener(authStateListener)
    }

    override fun onSupportNavigateUp() =
        NavigationUI.navigateUp(navController , appBarConfiguration)
}