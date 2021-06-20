package com.example.safephone2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.safephone2.database.AppDatabase
import com.example.safephone2.database.ProfileModel
import com.example.safephone2.fragments.HomeFragment
import com.example.safephone2.fragments.ProfilesFragment
import com.example.safephone2.fragments.SettingsFragment
import com.example.safephone2.service.Myservice
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment = HomeFragment()
        val profilesFragment = ProfilesFragment()
        val settingsFragment = SettingsFragment()
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        startService(Intent(applicationContext, Myservice::class.java))
        makeCurrentFragment(profilesFragment)



        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_profiles -> makeCurrentFragment(profilesFragment)
                R.id.ic_settings -> makeCurrentFragment(settingsFragment)
            }
            false
        }

    }
    private fun makeCurrentFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, fragment)
            transaction.commit()
        }
    }
}