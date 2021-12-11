/*
    Contributors: Jacob Strozyk, Siena South-Ciero
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

private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v2/"
private const val KEY = "9973533"

interface CocktailDBApiService {
    // Search for a cocktail by name
    @GET("$KEY/search.php?s=")
    fun getCocktails(@Query("s") cocktailName: String): Call<ResponseData>

    //Get Popular cocktails
    @GET("$KEY/popular.php")
    fun getPopular():  Call<ResponseData>
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
    //@Json(name="strCategroy")
    //val category: String,
    @Json(name="strGlass")
    val glassType: String,
    @Json(name="strInstructions")
    val instructions: String,
    @Json(name="strDrinkThumb")
    val image: String,

    //ingredients
    @Json(name="strIngredient1")
    val ingredient1: String,
    /*@Json(name="strIngredient2")
    val ingredient2: String,
    @Json(name="strIngredient3")
    val ingredient3: String,
    @Json(name="strIngredient4")
    val ingredient4: String,
    @Json(name="strIngredient5")
    val ingredient5: String,
    @Json(name="strIngredient6")
    val ingredient6: String,
    @Json(name="strIngredient7")
    val ingredient7: String,
    @Json(name="strIngredient8")
    val ingredient8: String,
    @Json(name="strIngredient9")
    val ingredient9: String,
    @Json(name="strIngredient10")
    val ingredient10: String,
    @Json(name="strIngredient11")
    val ingredient11: String,
    @Json(name="strIngredient12")
    val ingredient12: String,
    @Json(name="strIngredient13")
    val ingredient13: String,
    @Json(name="strIngredient14")
    val ingredient14: String,
    @Json(name="strIngredient15")
    val ingredient15: String,*/

    //measurements
    @Json(name="strMeasure1")
    val measurement1: String,
    /*@Json(name="strMeasure2")
    val measurement2: String,
    @Json(name="strMeasure3")
    val measurement3: String,
    @Json(name="strMeasure4")
    val measurement4: String,
    @Json(name="strMeasure5")
    val measurement5: String,
    @Json(name="strMeasure6")
    val measurement6: String,
    @Json(name="strMeasure7")
    val measurement7: String,
    @Json(name="strMeasure8")
    val measurement8: String,
    @Json(name="strMeasure9")
    val measurement9: String,
    @Json(name="strMeasure10")
    val measurement10: String,
    @Json(name="strMeasure11")
    val measurement11: String,
    @Json(name="strMeasure12")
    val measurement12: String,
    @Json(name="strMeasure13")
    val measurement13: String,
    @Json(name="strMeasure14")
    val measurement14: String,
    @Json(name="strMeasure15")
    val measurement15: String,*/
) : Parcelable