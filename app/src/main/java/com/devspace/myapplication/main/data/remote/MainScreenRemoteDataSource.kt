package com.devspace.myapplication.main.data.remote

import android.accounts.NetworkErrorException
import com.devspace.myapplication.common.data.model.Recipe

class MainScreenRemoteDataSource(
    private val mainService: MainService
) {

    suspend fun getRandomRecipes(): Result<List<Recipe>?> {
        return try {
            val response = mainService.getRandomRecipes()
            if (response.isSuccessful) {
                val recipes = response.body()?.recipes?.map {
                    Recipe(
                        id = it.id,
                        title = it.title,
                        summary = it.summary,
                        image = it.image
                    )
                }
                Result.success(recipes)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex:Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

}