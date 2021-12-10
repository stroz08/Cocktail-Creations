//Siena South-Ciero created the home fragment
package edu.uw.info448.cocktailcreations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //Adapter
        val adapter = CocktailListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.popularCocktailRecyclerView)
        recyclerView.adapter = adapter
        MainViewModel().getPopular()
        return view
    }
}