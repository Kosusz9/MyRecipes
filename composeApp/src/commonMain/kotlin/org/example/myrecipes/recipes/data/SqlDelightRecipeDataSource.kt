package org.example.myrecipes.recipes.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock
import org.example.myrecipes.core.data.ImageStorage
import org.example.myrecipes.database.RecipesDatabase
import org.example.myrecipes.recipes.domain.Recipe
import org.example.myrecipes.recipes.domain.RecipeDataSource

class SqlDelightRecipeDataSource(
    db: RecipesDatabase,
    private val imageStorage: ImageStorage
): RecipeDataSource {

    private val queries = db.recipeQueries

    override fun getRecipes(): Flow<List<Recipe>> {
        return queries
            .getRecipes()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { recipeEntities ->
                supervisorScope {
                    recipeEntities
                        .map {
                            async {
                                it.toRecipe(imageStorage)
                            }
                        }
                        .map {
                            it.await()
                        }
                }
            }
    }

    override fun getRecentRecipes(amount: Int): Flow<List<Recipe>> {
        return queries
            .getRecentRecipes(amount.toLong())
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { recipeEntities ->
                supervisorScope {
                    recipeEntities
                        .map {
                            async {
                                it.toRecipe(imageStorage)
                            }
                        }
                        .map {
                            it.await()
                        }
                }
            }
    }

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return queries
            .getFavoriteRecipes()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { recipeEntities ->
                supervisorScope {
                    recipeEntities
                        .map {
                            async {
                                it.toRecipe(imageStorage)
                            }
                        }
                        .map {
                            it.await()
                        }
                }
            }
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        val mainPhotoPath = recipe.mainPhotoBytes?.let {
            imageStorage.saveImage(it)
        }
        queries.insertRecipe(
            id = recipe.id,
            title = recipe.title,
            description = recipe.description,
            preparation = recipe.preparation,
            ingredients = recipe.ingredients,
            calories = recipe.calories?.toLong(),
            proteins = recipe.proteins?.toLong(),
            fats = recipe.fats?.toLong(),
            carbs = recipe.carbs?.toLong(),
            mainPhotoPath = mainPhotoPath,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            isFavorite = recipe.isFavorite
        )
    }

    override suspend fun deleteRecipe(id: Long) {
        val entity = queries.getRecipeById(id).executeAsOne()
        entity.mainPhotoPath?.let {
            imageStorage.deleteImage(it)
        }
        queries.deleteRecipe(id)
    }

    override suspend fun addOrRemoveRecipeFromFavorite(id: Long) {
        queries.addOrRemoveRecipeFromFavorite(id)
    }

}