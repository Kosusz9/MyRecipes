package org.example.myrecipes.recipes.domain

data class Recipe(
    val id: Long?,
    val title: String,
    val description: String?,
    val preparation: String,
    val ingredients: String,
    val calories: Int?,
    val proteins: Int?,
    val fats: Int?,
    val carbs: Int?,
    val mainPhotoBytes: ByteArray?,
    var isFavorite: Boolean = false
)
