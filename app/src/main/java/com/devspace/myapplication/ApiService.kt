package com.devspace.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("https://api.spoonacular.com/recipes/random?number=20")
    fun getRandomRecipes(): Call<RecipeResponse>

    @GET("recipes/{id}/information?includeNutrition=false")
    fun getRecipeInformation(@Path("id") id: String): Call<RecipeDto>

    @GET("/recipes/complexSearch?")
    fun searhRecipes(@Query("query") query: String): Call<SearchRecipesResponse>
}