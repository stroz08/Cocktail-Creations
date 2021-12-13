package edu.uw.info448.cocktailcreations

//Siena South-Ciero worked on this fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

private const val TAG = "Recipe Card"

class RecipeCardFragment : Fragment() {
    private var cocktailName: String? = null
    private var cocktailImg: String? = null
    private var ingredient1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: RecipeCardFragmentArgs by navArgs()
        cocktailName = args.cocktailName
        cocktailImg = args.cocktailImg
        ingredient1 = args.ingredient1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recipe_card, container, false)

        rootView.findViewById<TextView>(R.id.recipeCardName).text = cocktailName
        Glide.with(this).load("$cocktailImg").into(rootView.findViewById(R.id.recipeCardImg))
        rootView.findViewById<TextView>(R.id.ingredient_1).text = ingredient1

        return rootView
    }
}