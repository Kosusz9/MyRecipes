package org.example.myrecipes.recipes.domain

object RecipeValidator {
    fun validateRecipe(recipe: Recipe): ValidationResult {
        var result = ValidationResult()

        if(recipe.title.isBlank()) {
            result = result.copy(
                titleError = "Pole tytuł nie może być puste!"
            )
        }
        if(recipe.preparation.isBlank()) {
            result = result.copy(
                preparationError = "Pole przygotowanie nie może być puste!"
            )
        }
        if(recipe.ingredients.isBlank()) {
            result = result.copy(
                ingredientsError = "Pole składniki nie może być puste!"
            )
        }
        return result
    }

    data class ValidationResult(
        val titleError: String? = null,
        val preparationError: String? = null,
        val ingredientsError: String? = null
    )
}