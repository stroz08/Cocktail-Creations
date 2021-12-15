package edu.uw.info448.cocktailcreations

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

class AddDrinkFragment: Fragment() {

    private var uploadedImage = MutableLiveData<Bitmap>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_drink, container, false)

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
        view.findViewById<ImageButton>(R.id.camera_button).setOnClickListener {
            registerForActivityResult(
                ActivityResultContracts.TakePicturePreview()) {
                uploadedImage.postValue(it)
                view.findViewById<ImageView>(R.id.drink_photo).setImageBitmap(uploadedImage.value)
            }
        }

        //Finish button
        view.findViewById<Button>(R.id.finish_button).setOnClickListener{
            if (allBoxesFilled()) {
                submitCallback()
            } else {
                Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return view
    }

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

    private fun submitCallback() {
        val view = requireView()
        val drinkName = view.findViewById<TextView>(R.id.drink_name_box).text.toString()
        val category = view.findViewById<Spinner>(R.id.category_spinner).selectedItem.toString()
        val glass = view.findViewById<Spinner>(R.id.glass_spinner).selectedItem.toString()
        val instructions = view.findViewById<TextView>(R.id.instructions_box).text.toString()
        val cocktailPic = view.findViewById<ImageView>(R.id.drink_photo).drawable
            .toBitmap()
            .toString()
        //need to fix image location
        val newCocktail = Cocktail(drinkName, category, glass, instructions, cocktailPic, null)
    }

    private fun recipeMaker() {
        //TODO: fill this in
    }
}