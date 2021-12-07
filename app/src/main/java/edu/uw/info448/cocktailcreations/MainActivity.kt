//Siena South-Ciero worked on the navigation
package edu.uw.info448.cocktailcreations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val cameraFragment = CameraFragment()
    private val randomFragment = RandomFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFrag(homeFragment)

        //navigation
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> replaceFrag(homeFragment)
                R.id.searchFragment -> replaceFrag(searchFragment)
                R.id.cameraFragment -> replaceFrag(cameraFragment)
                R.id.randomFragment -> replaceFrag(randomFragment)
            }
            true
        }
    }

    private fun replaceFrag(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_fragment, fragment)
        transaction.commit()
    }
}