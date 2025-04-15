package com.devspace.myapplication.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {

    @Query("Select * from recipeentity")
    suspend fun getRecipes(): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipes: List<RecipeEntity>)
}