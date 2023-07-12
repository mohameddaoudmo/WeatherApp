package com.example.weatherapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    lateinit var binding : ActivityMainBinding
    private lateinit var connectivityObserver: ConnectivityObserver
    private var isconnected :Boolean=true

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot())
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.decorView.setSystemUiVisibility(window.decorView.systemUiVisibility)
//        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        navigationView = findViewById<NavigationView>(R.id.nav_view)
//        val actionBar = supportActionBar
//        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
//        actionBar!!.setDisplayShowHomeEnabled(true)
//        actionBar.setDisplayHomeAsUpEnabled(true)


        val navController = findNavController(this, R.id.nav_host_fragment)
//        setupWithNavController(navigationView, navController)
        setupWithNavController(binding.bottomNavigation, navController)

        if (!isconnected) {
            Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_LONG).apply {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.maincolor))
                show()
            }

    }}
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                actionBar?.hide()
//                drawerLayout.closeDrawer(GravityCompat.START)
//            } else {
//                actionBar?.show()
//
//                drawerLayout.openDrawer(GravityCompat.START)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }
    private fun hideSystemUI() {

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE

                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

}