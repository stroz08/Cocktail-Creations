// Brandon Ly created the Random Fragment

package edu.uw.info448.cocktailcreations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class RandomFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_random, container, false)

        // TODO: Add button event listener
        // TODO: Generate random ID number (11000-11060)
        // TODO: When button pressed look up cocktail by ID: www.thecocktaildb.com/api/json/v1/1/lookup.php?i=11007
        // TODO: Display cocktail ingredients + preview image

        return rootView
    }


}