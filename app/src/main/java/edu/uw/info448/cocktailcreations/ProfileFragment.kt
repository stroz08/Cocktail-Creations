package edu.uw.info448.cocktailcreations

import android.app.Dialog
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import edu.uw.info448.cocktailcreations.databinding.ActivityMainBinding
import org.w3c.dom.Text

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var viewModel: MainViewModel
    private lateinit var database: DatabaseReference

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

        //val name = database.database.app.name
        //Log.v(TAG, "Name: $name")
        //Log.v(TAG, "Name: $user.favorites")

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

        // Horizontal Layout
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // View Model
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Adapter
        val adapter = CocktailListAdapter(this)

        //Get Random cocktail
        //val recyclerView = view.findViewById<RecyclerView>(R.id.favoriteCocktailRecyclerView)
        //recyclerView.adapter = adapter
        //recyclerView.layoutManager = layoutManager
        //viewModel.randomCocktailData.observe(viewLifecycleOwner, Observer<List<Cocktail>> {
        //    Log.v(TAG, "Updating: $it")
        //    adapter.submitList(it)
        //})

        return view
    }

}