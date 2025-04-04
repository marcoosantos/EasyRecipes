package com.devspace.myapplication.main.data

import com.devspace.myapplication.common.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainService {

    @GET("recipes/random?number=15")
    suspend fun getRandomRecipes(): Response<RecipeResponse>

}