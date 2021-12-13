package edu.uw.info448.cocktailcreations

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

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
    val ingredient1: String = "",
    /*@Json(name="strIngredient2")
    val ingredient2: String = "",
    @Json(name="strIngredient3")
    val ingredient3: String = "",
    @Json(name="strIngredient4")
    val ingredient4: String = "",
    @Json(name="strIngredient5")
    val ingredient5: String = "",
    @Json(name="strIngredient6")
    val ingredient6: String = "",
    @Json(name="strIngredient7")
    val ingredient7: String = "",
    @Json(name="strIngredient8")
    val ingredient8: String = "",
    @Json(name="strIngredient9")
    val ingredient9: String = "",
    @Json(name="strIngredient10")
    val ingredient10: String = "",
    @Json(name="strIngredient11")
    val ingredient11: String = "",
    @Json(name="strIngredient12")
    val ingredient12: String = "",
    @Json(name="strIngredient13")
    val ingredient13: String = "",*/

    //these 2 crash the app
    /*@Json(name="strIngredient14")
    val ingredient14: String = "",
    @Json(name="strIngredient15")
    val ingredient15: String = "",*/

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

//idea to get the ingredient data
@Parcelize
data class recipeData(
    @Json(name="strMeasure1")
    val measurement1: String,
    @Json(name="strIngredient1")
    val ingredient1: String
) : Parcelable