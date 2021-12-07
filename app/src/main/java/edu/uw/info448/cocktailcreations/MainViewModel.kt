/*
    Contributors: Jacob Strozyk
 */

package edu.uw.info448.cocktailcreations

import android.content.ContentValues
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uw.info448.cocktailcreations.network.Cocktail
import edu.uw.info448.cocktailcreations.network.CocktailDBApi
import edu.uw.info448.cocktailcreations.network.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private var _cocktailData = MutableLiveData<List<Cocktail>>()

    val cocktailData : LiveData<List<Cocktail>>
        get() = _cocktailData

    fun getCocktails(v: View, query: String) {
        CocktailDBApi.retrofitService.getCocktails(query).enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val body = response.body()
                val cocktails = body!!.results
                _cocktailData.value = cocktails
                Log.v(TAG, "$cocktails")
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failure: ${t.message}")
            }
        })
    }
}