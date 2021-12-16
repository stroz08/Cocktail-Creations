// Contributors: Brandon Ly

package edu.uw.info448.cocktailcreations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(private val theData: MutableList<String>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val drinkName: TextView = view.findViewById<TextView>(R.id.favCocktailListName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_favorite_item,
            parent,
            false
        )

        val theHolder = ViewHolder(inflatedView)
        return theHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theItem = theData[position]
        holder.drinkName.text = theItem
    }

    override fun getItemCount() = theData.size

}