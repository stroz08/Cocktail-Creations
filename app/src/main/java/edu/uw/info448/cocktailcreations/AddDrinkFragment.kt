package edu.uw.info448.cocktailcreations

/* Sarah West wrote everything in this file */

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private const val TAG = "AddDrinkFragment"

class AddDrinkFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_drink, container, false)
        val takePicLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val extras = it.data!!.extras?.apply {
                    val imageBitmap = get("data") as Bitmap
                    val drinkPic = view.findViewById<ImageView>(R.id.drink_photo)
                    drinkPic.setImageBitmap(imageBitmap)
                }
            }
        }

        //Populate spinners
        val categorySpinner = view.findViewById<Spinner>(R.id.category_spinner)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_options,
            R.layout.support_simple_spinner_dropdown_item).also {
                categorySpinner.adapter = it
        }

        val glassSpinner = view.findViewById<Spinner>(R.id.glass_spinner)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.glass_options,
            R.layout.support_simple_spinner_dropdown_item).also {
                glassSpinner.adapter = it
        }

        // Camera Button
        view.findViewById<ImageButton>(R.id.camera_button).setOnClickListener{
            takePicLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }

        //Finish button
        view.findViewById<Button>(R.id.finish_button).setOnClickListener{
            if (allBoxesFilled()) {
                finishCallback()
            } else {
                Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return view
    }

    //Checks that all text boxes are filled
    private fun allBoxesFilled(): Boolean{
        val root = requireView() as ViewGroup
        val childCount = root.childCount
        val textViews: MutableList<TextView> = mutableListOf()
        for (i in 0 until childCount) {
            val child = root.getChildAt(i)
            if (child is EditText) textViews.add(child)
        }
        for (item in textViews) {
            if (item.text.toString() == "") return false
        }
        return true
    }

    // Callback function to submit drink to database + redirect to profile fragment
    private fun finishCallback() {
        val view = requireView()

        // Get form answers
        val drinkName = view.findViewById<TextView>(R.id.drink_name_box).text.toString()
        val category = view.findViewById<Spinner>(R.id.category_spinner).selectedItem.toString()
        val glass = view.findViewById<Spinner>(R.id.glass_spinner).selectedItem.toString()
        val instructions = view.findViewById<TextView>(R.id.instructions_box).text.toString()
        val cocktailPic = view.findViewById<ImageView>(R.id.drink_photo).drawable
            .toBitmap()
            .toString()
        val recipe = recipeMaker(view.findViewById(R.id.ingredient_box),
            view.findViewById(R.id.measurement_box))
        val newCocktail = Cocktail(drinkName, category, glass, instructions, cocktailPic,
            recipe)

        // Submit to database & return
        submitToDB(newCocktail)
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_fragment, ProfileFragment())
            .commit()

    }

    // Converts ingredient and measurement text box answers to list of ingredients
    private fun recipeMaker(ingredients: TextView, measurements: TextView): List<Ingredient> {
        val output: MutableList<Ingredient> = mutableListOf()
        val ingredientList = ingredients.text.toString().split(",")
        val measureList = measurements.text.toString().split(",")
        for (i in ingredientList.indices) {
            val ingredient = ingredientList[i]
            val measurement = measureList[i]
            if (measurement != " ") output.add(Ingredient(ingredient, measurement))
            else output.add(Ingredient(ingredient, null))
        }
        return output
    }

    // Creates hashmap to send to firebase
    private fun submitToDB (cocktail: Cocktail) {
        val user = Firebase.auth.currentUser
        val drinkMap = hashMapOf(
            "creator" to user!!.uid,
            "name" to cocktail.name,
            "category" to cocktail.category,
            "glass" to cocktail.glassType,
            "instructions" to cocktail.instructions,
            "image" to cocktail.image,
            "ingredients" to cocktail.recipe
        )
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(user!!.uid)
            .update("drinks", FieldValue.arrayUnion(drinkMap))
            .addOnSuccessListener {
                Log.d(TAG, "Drink added successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Failure when adding drink: $e")
            }
    }
}