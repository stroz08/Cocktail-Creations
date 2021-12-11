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

    private var _cocktailSearchData = MutableLiveData<List<Cocktail>>()

    val cocktailSearchData : LiveData<List<Cocktail>>
        get() = _cocktailSearchData

    private val _popularCocktailData: MutableLiveData<List<Cocktail>> by lazy {
        MutableLiveData<List<Cocktail>>().also {
            getPopular()
        }
    }

    val popularCocktailData: LiveData<List<Cocktail>>
        get() = _popularCocktailData

    //get cocktail
    fun getCocktails(v: View, query: String) {
        CocktailDBApi.retrofitService.getCocktails(query).enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val body = response.body()
                val cocktails = body!!.results
                _cocktailSearchData.value = cocktails
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
                _popularCocktailData.value = cocktails
                Log.v(TAG, "Cocktails$cocktails")
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failure: ${t.message}")
            }
        })
    }

    //get new cocktails
    fun getLatest() {
        CocktailDBApi.retrofitService.getLatest().enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val body = response.body()
                val cocktails = body!!.results
                _popularCocktailData.value = cocktails
                _popularCocktailData.postValue(cocktails)
                Log.v(TAG, "Cocktails$cocktails")
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failure: ${t.message}")
            }
        })
    }
}