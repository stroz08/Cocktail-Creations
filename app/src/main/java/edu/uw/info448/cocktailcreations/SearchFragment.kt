/*
    Contributors: Jacob Strozyk
 */

package edu.uw.info448.cocktailcreations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.uw.info448.cocktailcreations.network.Cocktail

private const val TAG = "SearchFragment"

class SearchFragment : Fragment() {

    private lateinit var adapter: CocktailListAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        // Initialize ViewModel and observer
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val cocktailObserver = Observer<List<Cocktail>> {
            Log.v(TAG, "Updating: $it")
            adapter.submitList(it)
        }
        viewModel.cocktailSearchData.observe(viewLifecycleOwner, cocktailObserver)

        // Initialize RecyclerView
        adapter = CocktailListAdapter()
        val recycler = rootView.findViewById<RecyclerView>(R.id.cocktail_list)
        recycler.layoutManager = LinearLayoutManager(this.context)
        recycler.adapter = adapter

        // Initialize click listener for the search button
        val searchButton = rootView.findViewById<Button>(R.id.search_button)
        searchButton.setOnClickListener {
            val inputContent = rootView.findViewById<EditText>(R.id.input).text
            viewModel.getCocktails(rootView, inputContent.toString())
        }

        return rootView
    }
}