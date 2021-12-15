//Siena South-Ciero worked on the navigation
package edu.uw.info448.cocktailcreations

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.os.Build
import android.widget.ScrollView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val randomFragment = RandomFragment()
    private val profileFragment = ProfileFragment()
    private lateinit var auth: FirebaseAuth
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFrag(homeFragment)
        auth = Firebase.auth

        //logout button
        findViewById<Button>(R.id.logout_button).setOnClickListener {
            logoutRedirect()
        }

        //navigation
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> replaceFrag(homeFragment)
                R.id.searchFragment -> replaceFrag(searchFragment)
                R.id.randomFragment -> replaceFrag(randomFragment)
                R.id.profileFragment -> replaceFrag(profileFragment)
            }
            true
        }
    }

    private fun replaceFrag(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_fragment, fragment)
        transaction.commit()
    }

    private fun logoutRedirect() {
        if (auth.currentUser != null) {
            auth.signOut()
        } else {
            Toast.makeText(baseContext, "Error: no user data found. Redirecting to login.",
                Toast.LENGTH_SHORT).show()
        }
        startActivity(Intent(this, LoginActivity::class.java))
    }
}