/*
    Contributors: Jacob Strozyk
 */

package edu.uw.info448.cocktailcreations

import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.TextView
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

    //get cocktail
    fun getCocktails(v: View, query: String) {
        CocktailDBApi.retrofitService.getCocktails(query).enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val body = response.body()
                val cocktails = body!!.results
                _cocktailData.value = cocktails
                Log.v(TAG, "Cocktails$cocktails")
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failure: ${t.message}")
            }
        })
    }

    //get popular cocktails
    fun getPopular() {
        CocktailDBApi.retrofitService.getPopular().enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val body = response.body()
                val cocktails = body!!.results
                //_cocktailData.value = cocktails
                _cocktailData.postValue(cocktails)
                Log.v(TAG, "Cocktails$cocktails")
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failure: ${t.message}")
            }
        })
    }
}