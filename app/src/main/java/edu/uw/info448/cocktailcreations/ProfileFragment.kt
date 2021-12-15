package edu.uw.info448.cocktailcreations

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        // Get current user
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Add Drink button
        view.findViewById<Button>(R.id.add_drink_button).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_fragment, AddDrinkFragment())
                .commit()
        }

        if (user != null) {
            val welcomeMessage = "Welcome: ${user.email}"
            view.findViewById<TextView>(R.id.profileWelcome).text = welcomeMessage
        } else {
            view.findViewById<TextView>(R.id.profileWelcome).text = "Please sign in"
        }



        return view
    }

}