package org.example.myrecipes.recipes.presentation

import org.example.myrecipes.recipes.domain.Recipe

sealed interface RecipeListEvent {
    object OnAddNewRecipeClick: RecipeListEvent
    object DismissRecipe: RecipeListEvent
    data class OnTitleChanged(val value: String): RecipeListEvent
    data class OnDescriptionChange(val value: String?): RecipeListEvent
    data class OnPreparationChange(val value: String): RecipeListEvent
    data class OnIngredientsChange(val value: String): RecipeListEvent
    data class OnCaloriesChange(val value: Int?): RecipeListEvent
    data class OnProteinsChange(val value: Int?): RecipeListEvent
    data class OnFatsChange(val value: Int?): RecipeListEvent
    data class OnCarbsChange(val value: Int?): RecipeListEvent
    class OnPhotoPicked(val bytes: ByteArray): RecipeListEvent
    object OnAddPhotoClicked: RecipeListEvent
    object SaveRecipe: RecipeListEvent
    data class SelectRecipe(val recipe: Recipe): RecipeListEvent
    data class EditRecipe(val recipe: Recipe): RecipeListEvent
    object AddOrRemoveRecipeFromFavorite: RecipeListEvent
    object DeleteRecipe: RecipeListEvent

}