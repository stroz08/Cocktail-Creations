package edu.uw.info448.cocktailcreations
/*
    Contributors: Jacob Strozyk, Siena South-Ciero
 */
import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.uw.info448.cocktailcreations.network.Cocktail

private const val TAG = "CocktailListAdapter"

class CocktailListAdapter(val context: Fragment, val layoutType: String) : ListAdapter<Cocktail, CocktailListAdapter.ViewHolder>(CocktailDiffCallback()) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cocktailName: TextView = view.findViewById(R.id.cocktailListName)
        val cocktailImg: ImageView = view.findViewById(R.id.cocktailListImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_cocktail_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val cocktailImg = item.image
        val cocktailName = item.name
        val ingredient1 = item.ingredient1

        holder.cocktailName.text = item!!.name
        Glide.with(context).load("$cocktailImg").into(holder.cocktailImg)

        //handle navigation
        holder.cocktailImg.setOnClickListener {
            Log.v(TAG, "$cocktailName")
            val action = HomeFragmentDirections.actionToRecipeFragment(cocktailName, cocktailImg, ingredient1)
            it.findNavController().navigate(action)
        }
    }

    /*override fun getItemCount(): Int {
        return .size
    }*/
}

class CocktailDiffCallback : DiffUtil.ItemCallback<Cocktail>() {
    override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem == newItem
    }
}