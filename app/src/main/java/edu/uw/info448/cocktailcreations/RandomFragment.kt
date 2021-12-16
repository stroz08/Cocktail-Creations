// Contributors: Brandon Ly

package edu.uw.info448.cocktailcreations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "RandomFragment"

class RandomFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_random, container, false)

        // Horizontal Layout
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // View Model
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Adapter
        val adapter = CocktailListAdapter(this)


        //Get Random cocktail
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.randomCocktailRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        viewModel.randomCocktailData.observe(viewLifecycleOwner, Observer<List<Cocktail>> {
            Log.v(TAG, "Updating: $it")
            adapter.submitList(it)
        })

        // Random Cocktail Button
        val generateRandomButton = rootView.findViewById<Button>(R.id.random_button)
        generateRandomButton.setOnClickListener(View.OnClickListener {
            viewModel.getRandom()
        })

        return rootView
    }
}
