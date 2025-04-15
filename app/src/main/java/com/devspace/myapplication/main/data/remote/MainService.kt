package com.devspace.myapplication.main.data.remote

import com.devspace.myapplication.common.data.remote.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainService {

    @GET("recipes/random?number=15")
    suspend fun getRandomRecipes(): Response<RecipeResponse>

}