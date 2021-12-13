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
import org.w3c.dom.Text

private const val TAG = "CocktailListAdapter"

class CocktailListAdapter(val context: Fragment, val layoutType: String) : ListAdapter<Cocktail, CocktailListAdapter.ViewHolder>(CocktailDiffCallback()) {
    private lateinit var view: View

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Cocktail Cards
        val cocktailName: TextView = view.findViewById(R.id.cocktailListName)
        val cocktailImg: ImageView = view.findViewById(R.id.cocktailListImg)

        //Recipe Cards
        val measurementList: TextView = view.findViewById(R.id.measurement)
        val ingredientList: TextView = view.findViewById(R.id.ingredient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = if(layoutType == "list_cocktail_item") {
            LayoutInflater.from(parent.context).inflate(R.layout.list_cocktail_item, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_ingredient_item, parent, false)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        //Cocktail cards
        if(layoutType == "list_cocktail_item") {
            val cocktailImg = item.image
            val cocktailName = item.name

            holder.cocktailName.text = item!!.name
            Glide.with(context).load("$cocktailImg").into(holder.cocktailImg)

            //handle navigation
            holder.cocktailImg.setOnClickListener {
                Log.v(TAG, "$cocktailName")
                val action = HomeFragmentDirections.actionToRecipeFragment(cocktailName, cocktailImg)
                it.findNavController().navigate(action)
            }
        } else { //Recipe cards
            holder.measurementList.text = item.measurement1
            holder.ingredientList.text = item.ingredient1
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