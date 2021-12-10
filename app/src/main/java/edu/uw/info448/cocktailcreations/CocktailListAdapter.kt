package edu.uw.info448.cocktailcreations
/*
    Contributors: Jacob Strozyk, Siena South-Ciero
 */
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.uw.info448.cocktailcreations.network.Cocktail

private const val TAG = "CocktailListAdapter"


class CocktailListAdapter() : ListAdapter<Cocktail, CocktailListAdapter.ViewHolder>(CocktailDiffCallback()) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cocktailName: TextView = view.findViewById(R.id.cocktailListName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_cocktail_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "we here")

        val item = getItem(position)
        holder.cocktailName.text = item!!.name
        Log.v(TAG, "NAME: $item!!.name")
    }
}

class CocktailDiffCallback : DiffUtil.ItemCallback<Cocktail>() {
    override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem == newItem
    }
}