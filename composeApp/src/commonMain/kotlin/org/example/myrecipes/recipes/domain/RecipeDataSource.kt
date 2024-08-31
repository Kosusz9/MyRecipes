package org.example.myrecipes.recipes.domain

import kotlinx.coroutines.flow.Flow

interface RecipeDataSource {
    fun getRecipes(): Flow<List<Recipe>>
    fun getRecentRecipes(amount: Int): Flow<List<Recipe>>
    fun getFavoriteRecipes(): Flow<List<Recipe>>
    suspend fun insertRecipe(recipe: Recipe)
    suspend fun deleteRecipe(id: Long)
    suspend fun addOrRemoveRecipeFromFavorite(id: Long)
}