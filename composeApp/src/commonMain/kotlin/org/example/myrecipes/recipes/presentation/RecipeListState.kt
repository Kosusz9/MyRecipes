package org.example.myrecipes.recipes.presentation

import org.example.myrecipes.recipes.domain.Recipe

data class RecipeListState(
    val recipes: List<Recipe> = emptyList(),
    val recentlyAddedRecipes: List<Recipe> = emptyList(),
    val favoriteRecipes: List<Recipe> = emptyList(),
    val selectedRecipe: Recipe? = null,
    val isAddRecipeSheetOpen: Boolean = false,
    val isSelectedRecipeSheetOpen: Boolean = false,
    val titleError: String? = null,
    val preparationError: String? = null,
    val ingredientsError: String? = null,
)
