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
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast

private const val TAG = "SearchFragment"

class SearchFragment : Fragment() {

    private lateinit var adapter: CocktailListAdapter
    private lateinit var viewModel: MainViewModel
    private var searchName = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        val radioGroup = rootView.findViewById<RadioGroup>(R.id.radio_group)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radiobutton_name -> {
                    searchName = true
                }
                R.id.radiobutton_ingredient -> {
                    searchName = false
                    Toast.makeText(rootView.context, "WARNING: Viewing cocktail details not supported in" +
                            " ingredient search mode because the API is janky.", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Initialize ViewModel and observer
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val cocktailObserver = Observer<List<Cocktail>> {
            Log.v(TAG, "Updating: $it")
            adapter.submitList(it)
        }
        viewModel.cocktailSearchData.observe(viewLifecycleOwner, cocktailObserver)

        // Initialize RecyclerView
        adapter = CocktailListAdapter(this)
        val recycler = rootView.findViewById<RecyclerView>(R.id.cocktail_list)
        recycler.layoutManager = LinearLayoutManager(this.context)
        recycler.adapter = adapter

        // Initialize click listener for the search button
        val searchButton = rootView.findViewById<Button>(R.id.search_button)
        searchButton.setOnClickListener {
            val inputContent = rootView.findViewById<EditText>(R.id.input).text
            if (searchName) {
                viewModel.getCocktails(rootView, inputContent.toString())
            } else {
                viewModel.getCocktailsByIngredient(rootView, inputContent.toString())
            }
        }

        return rootView
    }
}