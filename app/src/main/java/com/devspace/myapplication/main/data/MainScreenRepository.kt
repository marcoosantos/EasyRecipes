package com.devspace.myapplication.main.data

import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.main.data.local.MainScreenLocalDataSource
import com.devspace.myapplication.main.data.remote.MainScreenRemoteDataSource

class MainScreenRepository(
    private val local: MainScreenLocalDataSource,
    private val remote: MainScreenRemoteDataSource
) {

    suspend fun getRandomRecipes(): Result<List<Recipe>?> {
        return try {
           val result = remote.getRandomRecipes()
           if (result.isSuccess) {
               val recipesRemote = result.getOrNull() ?: emptyList()
               if (recipesRemote.isNotEmpty()) {
                   local.updateLocalItems(recipesRemote)
               }
               return Result.success(local.getRecipes())
           } else {
               val localData = local.getRecipes()
               if (localData.isEmpty()) {
                   return result
               } else {
                   Result.success(localData)
               }
           }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

}