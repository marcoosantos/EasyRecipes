package com.devspace.myapplication

import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.common.model.RecipeResponse
import com.devspace.myapplication.common.model.SearchRecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Lista
    @GET("https://api.spoonacular.com/recipes/random?number=20")
    fun getRandomRecipes(): Call<RecipeResponse>

    // Detalhe
    @GET("recipes/{id}/information?includeNutrition=false")
    fun getRecipeInformation(@Path("id") id: String): Call<RecipeDto>

    // Pesquisa?
    @GET("/recipes/complexSearch?")
    fun searhRecipes(@Query("query") query: String): Call<SearchRecipesResponse>
}