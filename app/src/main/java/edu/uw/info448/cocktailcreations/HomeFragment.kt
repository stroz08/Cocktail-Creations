//Siena South-Ciero created the home fragment
package edu.uw.info448.cocktailcreations

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import retrofit2.Callback
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.uw.info448.cocktailcreations.network.CocktailDBApi
import edu.uw.info448.cocktailcreations.network.CocktailDBApiService
import edu.uw.info448.cocktailcreations.network.ResponseData
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val adapter = CocktailListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.popularCocktailRecyclerView)
        recyclerView.adapter = adapter

        //martini is to test
        MainViewModel().getCocktails(view, "Martini")
        // Inflate the layout for this fragment
        return view
    }
}