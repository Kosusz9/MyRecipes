package org.example.myrecipes.di

import android.content.Context
import org.example.myrecipes.core.data.DatabaseDriverFactory
import org.example.myrecipes.core.data.ImageStorage
import org.example.myrecipes.database.RecipesDatabase
import org.example.myrecipes.recipes.data.SqlDelightRecipeDataSource
import org.example.myrecipes.recipes.domain.RecipeDataSource

actual class AppModule(
    private val context: Context
) {
    actual val recipeDataSource: RecipeDataSource by lazy {
        SqlDelightRecipeDataSource(
            db = RecipesDatabase(
                driver = DatabaseDriverFactory(context).create()
            ),
            imageStorage = ImageStorage(context)
        )
    }

}