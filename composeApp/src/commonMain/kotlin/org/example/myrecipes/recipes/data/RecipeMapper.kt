package org.example.myrecipes.recipes.data

import org.example.myrecipes.core.data.ImageStorage
import org.example.myrecipes.database.RecipeEntity
import org.example.myrecipes.recipes.domain.Recipe

suspend fun RecipeEntity.toRecipe(imageStorage: ImageStorage): Recipe {
    return Recipe(
        id = id,
        title = title,
        description = description,
        preparation = preparation,
        ingredients = ingredients,
        calories = calories?.toInt(),
        proteins = proteins?.toInt(),
        fats = fats?.toInt(),
        carbs = carbs?.toInt(),
        mainPhotoBytes = mainPhotoPath?.let { imageStorage.getImage(it) },
        isFavorite = isFavorite // != 0
    )
}