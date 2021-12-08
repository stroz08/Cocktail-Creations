/*
    Contributors: Jacob Strozyk
 */

package edu.uw.info448.cocktailcreations.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://thecocktaildb.com/api/json/v1/1/"

interface CocktailDBApiService {
    // Search for a cocktail by name
    @GET("search.php?s=")
    fun getCocktails(@Query("s") cocktailName: String): Call<ResponseData>
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

@Parcelize
data class ResponseData(
    @Json(name="drinks")
    val results : @RawValue List<Cocktail>
) : Parcelable

@Parcelize
data class Cocktail (
    @Json(name="strDrink")
    val name: String,
) : Parcelable