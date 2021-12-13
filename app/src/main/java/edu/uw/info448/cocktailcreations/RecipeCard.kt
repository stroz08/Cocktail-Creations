package edu.uw.info448.cocktailcreations

//Siena South-Ciero worked on this fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "Recipe Card"

class RecipeCardFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private var cocktailName: String? = null
    private var cocktailImg: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: RecipeCardFragmentArgs by navArgs()
        cocktailName = args.cocktailName
        cocktailImg = args.cocktailImg
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recipe_card, container, false)

        //viewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //Adapter
        /*val adapter = CocktailListAdapter(this, "list_ingredient_item")

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recipeRecyclerView)
        recyclerView?.adapter = adapter
        viewModel.popularCocktailData.observe(viewLifecycleOwner, Observer<List<Cocktail>> {
            Log.v(TAG, "Updating: $it")
            adapter.submitList(it)
        })*/

        rootView.findViewById<TextView>(R.id.recipeCardName).text = cocktailName
        Glide.with(this).load("$cocktailImg").into(rootView.findViewById(R.id.recipeCardImg))

        val heartBtn = rootView.findViewById<ImageView>(R.id.recipeHeartBtn);
        heartBtn.setOnClickListener() {
            heartBtn.setImageResource(R.drawable.filled_heart);
        }
        return rootView
    }
}