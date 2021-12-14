/*
    Contributors: Jacob Strozyk, Siena South-Ciero
 */

package edu.uw.info448.cocktailcreations.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import edu.uw.info448.cocktailcreations.ResponseData
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v2/"
private const val KEY = "9973533"

interface CocktailDBApiService {
    // Search for a cocktail by name
    @GET("$KEY/search.php?s=")
    fun getCocktails(@Query("s") cocktailName: String): Call<ResponseData>

    //Get Popular cocktails
    @GET("$KEY/popular.php")
    fun getPopular():  Call<ResponseData>

    //Get new cocktails
    @GET("$KEY/latest.php")
    fun getLatest():  Call<ResponseData>

    //Get random cocktail
    @GET("$KEY/random.php")
    fun getRandom(): Call<ResponseData>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object CocktailDBApi {
    val retrofitService: CocktailDBApiService by lazy {
        retrofit.create(CocktailDBApiService::class.java)
    }
}