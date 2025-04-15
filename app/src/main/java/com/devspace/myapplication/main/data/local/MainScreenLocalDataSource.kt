package com.devspace.myapplication.main.data.local

import com.devspace.myapplication.common.data.local.RecipeDao
import com.devspace.myapplication.common.data.local.RecipeEntity
import com.devspace.myapplication.common.data.model.Recipe

class MainScreenLocalDataSource(
    private val dao: RecipeDao
) {

    suspend fun getRecipes(): List<Recipe> {
        return getAllRecipes()
    }

    suspend fun updateLocalItems(recipes: List<Recipe>) {
        val entities = recipes.map {
            RecipeEntity(
                id = it.id,
                title = it.title,
                summary = it.summary,
                image = it.image
            )
        }
        dao.insert(entities)
    }

    private suspend fun getAllRecipes(): List<Recipe> {
        val entities = dao.getRecipes()

        return entities.map {
            Recipe(
                id = it.id,
                title = it.title,
                summary = it.summary,
                image = it.image
            )
        }
    }

}