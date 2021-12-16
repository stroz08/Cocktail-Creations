package edu.uw.info448.cocktailcreations

// Brandon Ly - Made profile welcome message, and favorites
// Sarah West - Added add drink button functionality, user drink retrieval and
// rawToCocktailConverter method

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {

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

        // Get Firestore instance
        val db = FirebaseFirestore.getInstance()

        // Displays favorites
        readFireStoreData(db)

        // Retrieve user drinks
        db.collection("users")
            .document(user!!.uid)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val rawData = it.result!!.get("drinks") as List<HashMap<String, Any>>
                    val userDrinkRecycler = view.findViewById<RecyclerView>(R.id.user_drink_list)
                    val userDrinkAdapter = UserDrinkAdapter(rawToCocktailConverter(rawData))
                    userDrinkRecycler.adapter = userDrinkAdapter
                    userDrinkRecycler.layoutManager = LinearLayoutManager(container?.context)
                }
            }

        // Add Drink button
        view.findViewById<Button>(R.id.add_drink_button).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_fragment, AddDrinkFragment())
                .commit()
        }

        return view
    }


    // Reads the firestore collection "Favorites" and appends to text view
    private fun readFireStoreData(db: FirebaseFirestore) {
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

    // Converts firebase data to List of Cocktails
    private fun rawToCocktailConverter(rawData: List<HashMap<String, Any>>): MutableList<Cocktail> {
        val output: MutableList<Cocktail> = mutableListOf()
        for (item in rawData) {
            val ingredientsRaw = item["ingredients"] as List<HashMap<String, String>>
            val ingredientList: MutableList<Ingredient> = mutableListOf()
            for (ingreItem in ingredientsRaw) {
                val newIngredient = Ingredient(ingreItem["ingredientName"].toString(),
                    ingreItem["measurement"].toString()
                )
                ingredientList.add(newIngredient)
            }
            val newCocktail = Cocktail(item.get("name").toString(),
                item.get("category").toString(), item.get("glass").toString(),
                item.get("instructions").toString(), item.get("image").toString(), ingredientList)
            output.add(newCocktail)
        }
        return output
    }
}