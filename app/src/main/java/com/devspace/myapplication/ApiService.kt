package com.devspace.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("https://api.spoonacular.com/recipes/random?number=20")
    fun getRandomRecipes(): Call<RecipeResponse>
}