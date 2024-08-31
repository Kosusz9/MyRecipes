package org.example.myrecipes.di

import org.example.myrecipes.core.data.DatabaseDriverFactory
import org.example.myrecipes.core.data.ImageStorage
import org.example.myrecipes.database.RecipesDatabase
import org.example.myrecipes.recipes.data.SqlDelightRecipeDataSource
import org.example.myrecipes.recipes.domain.RecipeDataSource

actual class AppModule {
    actual val recipeDataSource: RecipeDataSource by lazy {
        SqlDelightRecipeDataSource(
            db = RecipesDatabase(
                driver = DatabaseDriverFactory().create()
            ),
            imageStorage = ImageStorage()
        )
    }

}