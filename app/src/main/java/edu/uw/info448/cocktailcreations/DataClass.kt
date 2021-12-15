package edu.uw.info448.cocktailcreations

/*
    Contributors: Jacob Strozyk, Siena South-Ciero, Sarah West
 */

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ResponseData(
    @Json(name="drinks")
    val results : @RawValue List<RawCocktailData>
) : Parcelable

@Parcelize
data class ResponseDataByIngredient(
    @Json(name="drinks")
    val results : @RawValue List<RawCocktailByIngredientData>
) : Parcelable

//NOTE TO INSTRUCTOR: This is the format of the API response. The duplicate values are intentional
@Parcelize
data class RawCocktailData (
    @Json(name="idDrink")
    val id: Int,
    @Json(name="strDrink")
    val name: String,
    @Json(name="strCategory")
    val category: String?,
    @Json(name="strGlass")
    val glassType: String,
    @Json(name="strInstructions")
    val instructions: String,
    @Json(name="strDrinkThumb")
    val image: String,
    @Json(name="strIngredient1")
    val ingredient01: String?,
    @Json(name="strIngredient2")
    val ingredient02: String?,
    @Json(name="strIngredient3")
    val ingredient03: String?,
    @Json(name="strIngredient4")
    val ingredient04: String?,
    @Json(name="strIngredient5")
    val ingredient05: String?,
    @Json(name="strIngredient6")
    val ingredient06: String?,
    @Json(name="strIngredient7")
    val ingredient07: String?,
    @Json(name="strIngredient8")
    val ingredient08: String?,
    @Json(name="strIngredient9")
    val ingredient09: String?,
    @Json(name="strIngredient10")
    val ingredient10: String?,
    @Json(name="strIngredient11")
    val ingredient11: String?,
    @Json(name="strIngredient12")
    val ingredient12: String?,
    @Json(name="strIngredient13")
    val ingredient13: String?,
    @Json(name="strIngredient14")
    val ingredient14: String?,
    @Json(name="strIngredient15")
    val ingredient15: String?,
    @Json(name="strMeasure1")
    val measurement01: String?,
    @Json(name="strMeasure2")
    val measurement02: String?,
    @Json(name="strMeasure3")
    val measurement03: String?,
    @Json(name="strMeasure4")
    val measurement04: String?,
    @Json(name="strMeasure5")
    val measurement05: String?,
    @Json(name="strMeasure6")
    val measurement06: String?,
    @Json(name="strMeasure7")
    val measurement07: String?,
    @Json(name="strMeasure8")
    val measurement08: String?,
    @Json(name="strMeasure9")
    val measurement09: String?,
    @Json(name="strMeasure10")
    val measurement10: String?,
    @Json(name="strMeasure11")
    val measurement11: String?,
    @Json(name="strMeasure12")
    val measurement12: String?,
    @Json(name="strMeasure13")
    val measurement13: String?,
    @Json(name="strMeasure14")
    val measurement14: String?,
    @Json(name="strMeasure15")
    val measurement15: String?,
) : Parcelable

@Parcelize
data class RawCocktailByIngredientData(
    @Json(name = "strDrink")
    val name: String,
    @Json(name = "strDrinkThumb")
    val image: String,
    @Json(name = "idDrink")
    val id: Int
): Parcelable

@Parcelize
data class Cocktail(
    @Json(name = "idDrink")
    val id: Int,
    @Json(name = "strDrink")
    val name: String,
    @Json(name = "strCategory")
    val category: String?,
    @Json(name = "strGlass")
    val glassType: String?,
    @Json(name = "strInstructions")
    val instructions: String?,
    @Json(name = "strDrinkThumb")
    val image: String,
    val recipe: List<Ingredient>?
) : Parcelable

@Parcelize
data class Ingredient (
    val ingredientName: String,
    val measurement: String?,
) : Parcelable