package edu.uw.info448.cocktailcreations

//Siena South-Ciero worked on this fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

class RecipieCardFragment : Fragment() {
    private var cocktailName: String? = null
    private var cocktailImg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: RecipieCardFragmentArgs by navArgs()
        cocktailName = args.cocktailName
        cocktailImg = args.cocktailImg
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recipe_card, container, false)
        rootView.findViewById<TextView>(R.id.recipeCardName).text = cocktailName
        //Glide.with(this).load("$cocktailImg").into(rootView.findViewById(R.id.cocktailListImg))
        return rootView
    }
}