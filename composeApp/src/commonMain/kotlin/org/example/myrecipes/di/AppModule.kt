package org.example.myrecipes.di

import org.example.myrecipes.recipes.domain.RecipeDataSource

expect class AppModule {
    val recipeDataSource: RecipeDataSource
}