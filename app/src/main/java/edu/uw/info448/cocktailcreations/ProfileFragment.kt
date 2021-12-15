package edu.uw.info448.cocktailcreations

// Brandon Ly - Made profile welcome message, and favorites

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
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
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

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Welcome message to signed in User
        if (user != null) {
            val welcomeMessage = "Welcome: ${user.email}"
            view.findViewById<TextView>(R.id.profileWelcome).text = welcomeMessage
        } else {
            view.findViewById<TextView>(R.id.profileWelcome).text = "Please sign in"
        }

        // Displays favorites
        readFireStoreData()


        // Add Drink button
        view.findViewById<Button>(R.id.add_drink_button).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.profileFragment, AddDrinkFragment())
                .commit()
        }

        return view
    }


    // Reads the firestore collection "Favorites" and appends to text view
    fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("favorites")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        result.append(document.data.getValue("drink-name")).append(" ")
                    }
                    view?.findViewById<TextView>(R.id.favList)?.setText(result)
                }

            }

    }
}