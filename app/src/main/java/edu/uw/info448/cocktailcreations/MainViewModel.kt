/*
    Contributors: Jacob Strozyk, Siena South-Ciero, Sarah West, Brandon Ly
 */

package edu.uw.info448.cocktailcreations

import android.content.ContentValues
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uw.info448.cocktailcreations.network.CocktailDBApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.full.memberProperties

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
    private val _newCocktailData: MutableLiveData<List<Cocktail>> by lazy {
        MutableLiveData<List<Cocktail>>().also {
            getLatest()
        }
    }

    val randomCocktailData: LiveData<List<Cocktail>>
        get() = _randomCocktailData
    private val _randomCocktailData: MutableLiveData<List<Cocktail>> by lazy {
        MutableLiveData<List<Cocktail>>().also {
            getRandom()
        }
    }

    val newCocktailData: LiveData<List<Cocktail>>
        get() = _newCocktailData


    //get cocktail
    fun getCocktails(v: View, query: String) {
        CocktailDBApi.retrofitService.getCocktails(query).enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val body = response.body()
                val cocktails = body!!.results
                _cocktailSearchData.value = rawToCocktailConverter(cocktails)
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
                _popularCocktailData.value = rawToCocktailConverter(cocktails)
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
                _newCocktailData.value = rawToCocktailConverter(cocktails)
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failure: ${t.message}")
            }
        })
    }

    //get random cocktail
    fun getRandom() {
        CocktailDBApi.retrofitService.getRandom().enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val body = response.body()
                val cocktail = body!!.results
                _randomCocktailData.value = rawToCocktailConverter(cocktail)
                Log.v(TAG, "HERE: ${_randomCocktailData.value}")
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failure: ${t.message}")
            }
        })
    }

    private fun rawToCocktailConverter(baseList: List<RawCocktailData>): List<Cocktail> {
        val output: MutableList<Cocktail> = mutableListOf()
        for (raw in baseList) {
            val ingredientList: MutableList<Ingredient> = mutableListOf()
            val rawIngreProps = RawCocktailData::class.memberProperties.filter { prop ->
                prop.name.startsWith("ingredient") && prop.get(raw) != null
            }
            val rawMeasurementProps = RawCocktailData::class.memberProperties.filter { prop ->
                prop.name.startsWith("measurement")
            }
            for (i in rawIngreProps.indices) {
                val name = rawIngreProps[i].get(raw)
                val measurement = rawMeasurementProps[i].get(raw)
                if (measurement != null) ingredientList.add(Ingredient(name.toString(),
                    measurement.toString()))
                else ingredientList.add(Ingredient(name.toString(), null))
            }
            output.add(Cocktail(raw.id, raw.name, raw.category, raw.glassType,
                raw.instructions, raw.image, ingredientList))
        }
        return output
    }
}