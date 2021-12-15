package edu.uw.info448.cocktailcreations

//Siena South-Ciero and Sarah West worked on this fragment
//Brandon Ly worked on heart button and storing favorites to firebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private const val TAG = "Recipe Card"

class RecipeCardFragment : Fragment() {

    private var cocktailName: String? = null
    private var cocktailImg: String? = null
    private var recipe: List<Ingredient>? = null
    private var directions: String? = null
    private val favorite: MutableMap<String, Any> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cocktailName = it.getString("cocktailName")
            cocktailImg = it.getString("cocktailImg")
            recipe = it.getParcelableArrayList("recipe")
            directions = it.getString("directions")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recipe_card, container, false)

        rootView.findViewById<TextView>(R.id.recipeCardName).text = cocktailName
        Glide.with(this).load("$cocktailImg").into(rootView.findViewById(R.id.recipeCardImg))
        val size = recipe!!.size - 1
        var result = ""
        for(i in 0 .. size) {
            val index = recipe!![i]
            if(index.measurement != null) {
                result += index.measurement + " "
            }
            result += index.ingredientName + "\n"
        }
        rootView.findViewById<TextView>(R.id.recipeList).text = result
        rootView.findViewById<TextView>(R.id.directions_text).text = directions

        // Create heart/favorite button
        val heartBtn = rootView.findViewById<CheckBox>(R.id.recipeHeartBtn)

        if (favorite.contains(cocktailName)) {
            heartBtn.isChecked
        }

        // Checks if heart button is clicked or not. Saves current cocktail name to firestore
        heartBtn.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                //showToast("$cocktailName added to Favorites")
                if (!favorite.contains(cocktailName)) {
                    saveFireStore(cocktailName.toString())
                }
            } else {
                showToast("$cocktailName removed from Favorites")
            }
        }

        return rootView
    }

    // Displays toast to user, made for displaying heart button status
    private fun showToast(str: String) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    // Saves data to firestore
    private fun saveFireStore(drinkName: String) {
        // getting firestore instance
        val db = FirebaseFirestore.getInstance()
        // creating hashmap for favorite collection
        favorite["drink-name"] = drinkName

        // adds drink to database and sends a toast to indicate status
        db.collection("favorites")
            .add(favorite)
            .addOnSuccessListener {
                Toast.makeText(context, "$drinkName added to Favorites in DB", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to add to DB", Toast.LENGTH_SHORT).show()
            }

    }

    //need a remove method
}