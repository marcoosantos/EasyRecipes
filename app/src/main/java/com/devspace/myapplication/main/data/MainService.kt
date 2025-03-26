package com.devspace.myapplication.main.data

import com.devspace.myapplication.common.model.RecipeResponse
import retrofit2.Call
import retrofit2.http.GET

interface MainService {

    @GET("https://api.spoonacular.com/recipes/random?number=20")
    fun getRandomRecipes(): Call<RecipeResponse>

}