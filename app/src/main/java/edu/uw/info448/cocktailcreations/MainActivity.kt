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
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.jetbrains.annotations.NotNull




class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val randomFragment = RandomFragment()
    private val profileFragment = ProfileFragment()
    private lateinit var auth: FirebaseAuth

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
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> replaceFrag(homeFragment)
                R.id.searchFragment -> replaceFrag(searchFragment)
                R.id.randomFragment -> replaceFrag(randomFragment)
                R.id.profileFragment -> replaceFrag(profileFragment)
            }
            true
        }
    }

    //save state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun replaceFrag(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_fragment, fragment)
        transaction.commit()
    }

    private fun logoutRedirect() {
        if (auth.currentUser != null) {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            Toast.makeText(baseContext, "Error: no user data found", Toast.LENGTH_SHORT).show()
        }
    }
}